package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.dto.PurchaseDTO;
import bg.uni.plovdiv.model.BarcodeAndQuantity;
import bg.uni.plovdiv.model.OrderType;
import bg.uni.plovdiv.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchase")
@Tag(name = "Purchase endpoints")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    @Operation(summary = "Get all purchases")
    public List<PurchaseDTO> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/getById")
    @Operation(summary = "Get a purchase by ID")
    public Optional<PurchaseDTO> getPurchaseById(@NotBlank @RequestParam Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new purchase")
    public boolean addPurchase(@NotBlank @RequestBody String bulstat, @Valid @RequestBody List<BarcodeAndQuantity> barcodeAndQuantityList, @RequestBody @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99") double totalPrice, @RequestBody @NotNull OrderType orderType) {
        return purchaseService.addPurchase(bulstat, barcodeAndQuantityList, totalPrice, orderType);
    }
}
