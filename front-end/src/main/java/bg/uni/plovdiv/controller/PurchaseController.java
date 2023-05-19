package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.PurchaseService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public String purchase() {
        return "purchase/purchase";
    }

    @GetMapping("/get-all")
    public String purchaseAllPurchase() {
        return "purchase/all-purchase";
    }

    @GetMapping("/get-by-id")
    public String purchaseById() {
        return "purchase/purchase-by-id";
    }

    @GetMapping("/add")
    public String addPurchase() {
        return "purchase/add-purchase";
    }

    @PostMapping("/get-all")
    public String getAllPurchase(Model model) {
        String allPurchases = purchaseService.getAllPurchases();
        model.addAttribute("allPurchases", allPurchases);
        return "purchase/all-purchase";
    }

    @PostMapping("/get-by-id")
    public String getPurchaseById(@NotBlank @RequestParam Long id, Model model) {
        String purchaseById = purchaseService.getPurchasesById(id);
        model.addAttribute("purchaseById", purchaseById);
        return "purchase/purchase-by-id";
    }

    @PostMapping("/add")
    public String addPurchase(@NotBlank @Length(max = 35) @RequestParam String bulstat,
                              @DecimalMin(value = "0.00") @DecimalMax(value = "9999999999.99") double totalPrice,
                              @NotNull @RequestParam("barcode") List<String> barcodes,
                              @NotNull @RequestParam("quantity") List<Integer> quantities,
                              @NotNull String orderType, Model model) {
        String addPurchase = purchaseService.addPurchase(bulstat, totalPrice, barcodes, quantities, orderType);
        model.addAttribute("addPurchase", addPurchase);
        return "purchase/add-purchase";
    }
}
