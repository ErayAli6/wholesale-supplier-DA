package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.ProductService;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

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

    @GetMapping("/register")
    public String registerProduct() {
        return "product/register-product";
    }

    @GetMapping("/edit")
    public String editProduct() {
        return "product/edit-product";
    }

    @PostMapping("/get-all")
    public String getAllProducts(Model model) {
        String allProducts = productService.getAllProducts();
        model.addAttribute("allProducts", allProducts);
        return "product/all-product";
    }

    @PostMapping("/get-by-barcode")
    public String getProductByBarcode(@NotBlank @Length(max = 35) @RequestParam String barcode, Model model) {
        String productByBarcode = productService.getProductByBarcode(barcode);
        model.addAttribute("productByBarcode", productByBarcode);
        return "product/product-by-barcode";
    }

    @PostMapping("/remove")
    public String removeProduct(@NotBlank @Length(max = 35) @RequestParam String barcode, Model model) {
        String removeProduct = productService.removeProduct(barcode);
        model.addAttribute("removeProduct", removeProduct);
        return "product/product-remove";
    }

    @PostMapping("/register")
    public String registerProduct(@NotBlank @Length(max = 35) @RequestParam String barcode, @RequestParam @Length(max = 35) String brand,
                                  @RequestParam @Length(max = 35) String modelProduct, @RequestParam @Length(max = 35) String category, @PositiveOrZero @RequestParam int quantity,
                                  @DecimalMin(value = "0.00") @DecimalMax(value = "9999999999.99") double price,
                                  @RequestParam LocalDate localDate, @RequestParam byte[] photo, Model model) {
        String registerProduct = productService.registerProduct(barcode, brand, modelProduct, category, quantity, price, localDate, photo);
        model.addAttribute("registerProduct", registerProduct);
        return "product/register-product";
    }

    @PostMapping("/edit")
    public String editProduct(@NotBlank @Length(max = 35) @RequestParam String barcode, @RequestParam @Length(max = 35) String brand,
                              @RequestParam @Length(max = 35) String modelProduct, @RequestParam @Length(max = 35) String category, @PositiveOrZero @RequestParam int quantity,
                              @DecimalMin(value = "0.00") @DecimalMax(value = "9999999999.99") double price,
                              @RequestParam LocalDate localDate, @RequestParam byte[] photo, Model model) {
        String editProduct = productService.editProduct(barcode, brand, modelProduct, category, quantity, price, localDate, photo);
        model.addAttribute("editProduct", editProduct);
        return "product/edit-product";
    }
}
