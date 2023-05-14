package bg.uni.plovdiv.service;

import bg.uni.plovdiv.dto.CompanyDTO;
import bg.uni.plovdiv.dto.ProductDTO;
import bg.uni.plovdiv.dto.PurchaseDTO;
import bg.uni.plovdiv.model.*;
import bg.uni.plovdiv.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final CompanyService companyService;

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

    public boolean addPurchase(String bulstat, List<BarcodeAndQuantity> barcodeAndQuantities, double totalPrice, OrderType orderType) {
        Company company = companyService.getCompanyByBulstat(bulstat)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        List<Product> productList = getProductList(barcodeAndQuantities);
        Purchase purchase = createPurchase(company, productList, totalPrice, orderType);
        try {
            updateProductQuantities(productList, orderType);
            purchaseRepository.save(purchase);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    private List<Product> getProductList(List<BarcodeAndQuantity> barcodeAndQuantities) {
        return barcodeAndQuantities.stream()
                .map(barcodeAndQuantity -> {
                    Optional<Product> productByBarcode = productService.getProductByBarcode(barcodeAndQuantity.getBarcode());
                    if (productByBarcode.isEmpty()) {
                        throw new IllegalArgumentException("Product with barcode :" + barcodeAndQuantity.getBarcode() + " is not found");
                    }
                    return productByBarcode.get().toBuilder()
                            .quantity(barcodeAndQuantity.getQuantity())
                            .build();
                })
                .toList();
    }

    private Purchase createPurchase(Company company, List<Product> productList, double totalPrice, OrderType orderType) {
        return Purchase.builder()
                .company(company)
                .orderProducts(productList)
                .orderDateTime(LocalDateTime.now())
                .totalPrice(totalPrice)
                .orderType(orderType)
                .build();
    }

    private void updateProductQuantities(List<Product> productList, OrderType orderType) {
        if (orderType == OrderType.DELIVERY) {
            productList.forEach(productService::deliveryProduct);
        } else if (orderType == OrderType.SELLING) {
            productList.forEach(productService::sellingProduct);
        } else {
            throw new IllegalArgumentException("The order does not have order type!");
        }
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
                        .build())
                .toList();
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
}
