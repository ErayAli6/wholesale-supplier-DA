package bg.uni.plovdiv.service;

import bg.uni.plovdiv.dto.ProductDTO;
import bg.uni.plovdiv.model.Product;
import bg.uni.plovdiv.repository.ProductRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .barcode(product.getBarcode())
                        .brand(product.getBrand())
                        .model(product.getModel())
                        .category(product.getCategory())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .manufactureDate(product.getManufactureDate())
                        .photo(product.getPhoto())
                        .isAvailable(product.isAvailable())
                        .build())
                .toList();
    }

    public Optional<ProductDTO> getProductByBarcode(@NotBlank @Length(max = 35) String barcode) {
        Product product = productRepository.findByBarcode(barcode);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(ProductDTO.builder()
                .barcode(product.getBarcode())
                .brand(product.getBrand())
                .model(product.getModel())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .manufactureDate(product.getManufactureDate())
                .photo(product.getPhoto())
                .isAvailable(product.isAvailable())
                .build());
    }

    public boolean registerProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .barcode(productDTO.getBarcode())
                .brand(productDTO.getBrand())
                .model(productDTO.getModel())
                .category(productDTO.getCategory())
                .quantity(productDTO.getQuantity())
                .price(productDTO.getPrice())
                .manufactureDate(productDTO.getManufactureDate())
                .isAvailable(productDTO.isAvailable())
                .build();
        try {
            productRepository.save(product);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public boolean editProduct(ProductDTO productDTO) {
        Product product = productRepository.findByBarcode(productDTO.getBarcode());
        if (product == null) {
            return false;
        }
        product.setBrand(productDTO.getBrand());
        product.setModel(productDTO.getModel());
        product.setCategory(productDTO.getCategory());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setManufactureDate(productDTO.getManufactureDate());
        product.setAvailable(productDTO.isAvailable());
        try {
            productRepository.save(product);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public boolean removeProduct(@NotBlank @Length(max = 35) String barcode) {
        Product product = productRepository.findByBarcode(barcode);
        if (product == null) {
            return false;
        }
        try {
            productRepository.delete(product);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    protected void deliveryProduct(ProductDTO productDTO) {
        Product foundProduct = productRepository.findByBarcode(productDTO.getBarcode());
        if (foundProduct == null) {
            throw new IllegalArgumentException("The product was not found!");
        }
        foundProduct.setQuantity(foundProduct.getQuantity() + productDTO.getQuantity());
        if (foundProduct.getQuantity() > 0) {
            foundProduct.setAvailable(true);
        }
        productRepository.save(foundProduct);
    }

    protected void sellingProduct(ProductDTO productDTO) {
        Product foundProduct = productRepository.findByBarcode(productDTO.getBarcode());
        if (foundProduct == null) {
            throw new IllegalArgumentException("The product was not found!");
        }
        int currentQuantity = foundProduct.getQuantity() - productDTO.getQuantity();
        if (currentQuantity < 0) {
            throw new IllegalArgumentException("There is not enough quantity of a product with barcode: " + productDTO.getBarcode());
        }
        foundProduct.setQuantity(currentQuantity);
        if (foundProduct.getQuantity() == 0) {
            foundProduct.setAvailable(false);
        }
        productRepository.save(foundProduct);
    }
}
