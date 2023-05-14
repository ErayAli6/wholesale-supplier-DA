package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.ProductService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String product() {
        return "product/product";
    }

    @GetMapping("/get-all")
    public String productAllProduct() {
        return "product/all-product";
    }

    @GetMapping("/get-by-barcode")
    public String productByBarcode() {
        return "product/product-by-barcode";
    }

    @GetMapping("/remove")
    public String removeProduct() {
        return "product/product-remove";
    }

    @PostMapping("/get-all")
    public String getAllProducts(Model model) {
        String allProducts = productService.getAllProducts();
        model.addAttribute("allProducts", allProducts);
        return "product/all-product";
    }

    @PostMapping("/get-by-barcode")
    public String getProductByBarcode(@NotBlank @Length(max = 35) @RequestParam String barcode, Model model) {
        String product = productService.getProductByBarcode(barcode);
        model.addAttribute("product", product);
        return "product/product-by-barcode";
    }

    @PostMapping("/remove")
    public String removeProduct(@NotBlank @Length(max = 35) @RequestParam String barcode, Model model) {
        String response = productService.removeProduct(barcode);
        model.addAttribute("response", response);
        return "product/product-remove";
    }
}
