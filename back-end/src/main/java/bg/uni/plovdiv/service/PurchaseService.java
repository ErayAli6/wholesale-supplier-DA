package bg.uni.plovdiv.service;

import bg.uni.plovdiv.dto.CompanyDTO;
import bg.uni.plovdiv.dto.ProductDTO;
import bg.uni.plovdiv.dto.PurchaseDTO;
import bg.uni.plovdiv.model.Company;
import bg.uni.plovdiv.model.OrderType;
import bg.uni.plovdiv.model.Product;
import bg.uni.plovdiv.model.Purchase;
import bg.uni.plovdiv.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final ProductService productService;

    public List<PurchaseDTO> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(purchase -> PurchaseDTO.builder()
                        .id(purchase.getId())
                        .company(getCompanyDTO(purchase.getCompany()))
                        .orderProducts(getProductDTOs(purchase.getOrderProducts()))
                        .orderDateTime(purchase.getOrderDateTime())
                        .totalPrice(purchase.getTotalPrice())
                        .orderType(purchase.getOrderType())
                        .build())
                .toList();
    }

    public Optional<PurchaseDTO> getPurchaseById(Long id) {
        return purchaseRepository.findById(id)
                .map(purchase -> PurchaseDTO.builder()
                        .id(purchase.getId())
                        .company(getCompanyDTO(purchase.getCompany()))
                        .orderProducts(getProductDTOs(purchase.getOrderProducts()))
                        .orderDateTime(purchase.getOrderDateTime())
                        .totalPrice(purchase.getTotalPrice())
                        .orderType(purchase.getOrderType())
                        .build());
    }

    public boolean addPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = Purchase.builder()
                .company(getCompany(purchaseDTO.getCompany()))
                .orderProducts(getProducts(purchaseDTO.getOrderProducts()))
                .orderDateTime(purchaseDTO.getOrderDateTime())
                .totalPrice(purchaseDTO.getTotalPrice())
                .orderType(purchaseDTO.getOrderType())
                .build();
        try {
            purchaseRepository.save(purchase);
            if (purchaseDTO.getOrderType() == OrderType.DELIVERY) {
                purchaseDTO.getOrderProducts().forEach(productService::deliveryProduct);
            } else if (purchaseDTO.getOrderType() == OrderType.SELLING) {
                purchaseDTO.getOrderProducts().forEach(productService::sellingProduct);
            } else {
                throw new IllegalArgumentException("The order does not have order type!");
            }
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    private Company getCompany(CompanyDTO companyDTO) {
        return Company.builder()
                .bulstat(companyDTO.getBulstat())
                .name(companyDTO.getName())
                .address(companyDTO.getAddress())
                .vatNumber(companyDTO.getVatNumber())
                .phoneNumber(companyDTO.getPhoneNumber())
                .email(companyDTO.getEmail())
                .build();
    }

    private CompanyDTO getCompanyDTO(Company company) {
        return CompanyDTO.builder()
                .bulstat(company.getBulstat())
                .name(company.getName())
                .address(company.getAddress())
                .vatNumber(company.getVatNumber())
                .phoneNumber(company.getPhoneNumber())
                .email(company.getEmail())
                .build();
    }

    private List<Product> getProducts(List<ProductDTO> productDTOS) {
        return productDTOS
                .stream()
                .map(productDTO -> Product.builder()
                        .barcode(productDTO.getBarcode())
                        .brand(productDTO.getBrand())
                        .model(productDTO.getModel())
                        .category(productDTO.getCategory())
                        .quantity(productDTO.getQuantity())
                        .price(productDTO.getPrice())
                        .manufactureDate(productDTO.getManufactureDate())
                        .photo(productDTO.getPhoto())
                        .isAvailable(productDTO.isAvailable())
                        .build())
                .toList();
    }

    private List<ProductDTO> getProductDTOs(List<Product> products) {
        return products
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
}
