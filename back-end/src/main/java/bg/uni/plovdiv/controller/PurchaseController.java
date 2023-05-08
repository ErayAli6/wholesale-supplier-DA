package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.dto.PurchaseDTO;
import bg.uni.plovdiv.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    @Operation(summary = "Get a purchase by ID")
    public Optional<PurchaseDTO> getPurchaseById(@NotBlank @RequestParam Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new purchase")
    public boolean addPurchase(@NotNull @Valid @RequestBody PurchaseDTO purchaseDTO) {
        return purchaseService.addPurchase(purchaseDTO);
    }
}
