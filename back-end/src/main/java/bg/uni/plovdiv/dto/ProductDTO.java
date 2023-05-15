package bg.uni.plovdiv.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
public class ProductDTO {

    @NotBlank
    @Length(max = 35)
    private String barcode;

    @Length(max = 35)
    private String brand;

    @Length(max = 35)
    private String model;

    @Length(max = 35)
    private String category;

    @PositiveOrZero
    private int quantity;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private double price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate manufactureDate;

    private byte[] photo;
}
