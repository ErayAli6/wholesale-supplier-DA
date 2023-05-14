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
                        .build())
                .toList();
    }

    public Optional<ProductDTO> getProductDTOByBarcode(@NotBlank @Length(max = 35) String barcode) {
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

    protected void deliveryProduct(Product product) {
        Product foundProduct = productRepository.findByBarcode(product.getBarcode());
        if (foundProduct == null) {
            throw new IllegalArgumentException("The product was not found!");
        }
        foundProduct.setQuantity(foundProduct.getQuantity() + product.getQuantity());
        productRepository.save(foundProduct);
    }

    protected void sellingProduct(Product product) {
        Product foundProduct = productRepository.findByBarcode(product.getBarcode());
        if (foundProduct == null) {
            throw new IllegalArgumentException("The product was not found!");
        }
        int currentQuantity = foundProduct.getQuantity() - product.getQuantity();
        if (currentQuantity < 0) {
            throw new IllegalArgumentException("There is not enough quantity of a product with barcode: " + product.getBarcode());
        }
        foundProduct.setQuantity(currentQuantity);
        productRepository.save(foundProduct);
    }

    protected Optional<Product> getProductByBarcode(@NotBlank @Length(max = 35) String barcode) {
        Product product = productRepository.findByBarcode(barcode);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(product);
    }
}
