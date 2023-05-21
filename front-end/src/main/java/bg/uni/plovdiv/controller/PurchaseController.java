package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.PurchaseService;
import bg.uni.plovdiv.service.TokenService;
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

    private final TokenService tokenService;

    @GetMapping
    public String purchase() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "purchase/purchase";
    }

    @GetMapping("/get-all")
    public String purchaseAllPurchase() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "purchase/all-purchase";
    }

    @GetMapping("/get-by-id")
    public String purchaseById() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "purchase/purchase-by-id";
    }

    @GetMapping("/add")
    public String addPurchase() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "purchase/add-purchase";
    }

    @PostMapping("/get-all")
    public String getAllPurchase(Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String allPurchases = purchaseService.getAllPurchases(tokenService.getToken());
        model.addAttribute("allPurchases", allPurchases);
        return "purchase/all-purchase";
    }

    @PostMapping("/get-by-id")
    public String getPurchaseById(@NotBlank @RequestParam Long id, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String purchaseById = purchaseService.getPurchasesById(id, tokenService.getToken());
        model.addAttribute("purchaseById", purchaseById);
        return "purchase/purchase-by-id";
    }

    @PostMapping("/add")
    public String addPurchase(@NotBlank @Length(max = 35) @RequestParam String bulstat,
                              @DecimalMin(value = "0.00") @DecimalMax(value = "9999999999.99") double totalPrice,
                              @NotNull @RequestParam("barcode") List<String> barcodes,
                              @NotNull @RequestParam("quantity") List<Integer> quantities,
                              @NotNull String orderType, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String addPurchase = purchaseService.addPurchase(bulstat, totalPrice, barcodes, quantities, orderType, tokenService.getToken());
        model.addAttribute("addPurchase", addPurchase);
        return "purchase/add-purchase";
    }
}
