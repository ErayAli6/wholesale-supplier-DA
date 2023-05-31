package bg.uni.plovdiv.dto;

import bg.uni.plovdiv.model.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDTO {

    @NotNull
    private Long id;

    @NotNull
    @Valid
    private CompanyDTO company;

    @NotNull
    private List<@Valid ProductDTO> orderProducts;

    private LocalDateTime orderDateTime;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private double totalPrice;

    @NotNull
    private OrderType orderType;
}
