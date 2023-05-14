package bg.uni.plovdiv.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BarcodeAndQuantity {

    @NotBlank
    private String barcode;

    @Positive
    private int quantity;
}
