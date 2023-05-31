package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.dto.ProductDTO;
import bg.uni.plovdiv.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/product", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Product endpoints")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", security = @SecurityRequirement(name = "bearerAuth"))
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getByBarcode")
    @Operation(summary = "Get a product by barcode", security = @SecurityRequirement(name = "bearerAuth"))
    public Optional<ProductDTO> getProductByBarcode(@NotBlank @Length(max = 35) @RequestParam String barcode) {
        return productService.getProductDTOByBarcode(barcode);
    }

    @PostMapping
    @Operation(summary = "Register a new product", security = @SecurityRequirement(name = "bearerAuth"))
    public boolean registerProduct(@NotNull @Valid @RequestBody ProductDTO productDTO) {
        return productService.registerProduct(productDTO);
    }

    @PutMapping
    @Operation(summary = "Edit an existing product", security = @SecurityRequirement(name = "bearerAuth"))
    public boolean editProduct(@NotNull @Valid @RequestBody ProductDTO productDTO) {
        return productService.editProduct(productDTO);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove a product by barcode", security = @SecurityRequirement(name = "bearerAuth"))
    public boolean removeProduct(@NotBlank @Length(max = 35) @RequestParam String barcode) {
        return productService.removeProduct(barcode);
    }
}
