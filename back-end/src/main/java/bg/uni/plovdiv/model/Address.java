package bg.uni.plovdiv.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Address {

    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(max = 30)
    private String country;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(max = 30)
    private String state;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(max = 30)
    private String city;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Length(max = 30)
    private String street;

    @Column(nullable = false, length = 10)
    @NotBlank
    @Length(max = 10)
    private String number;

    @Column(nullable = false, length = 10)
    @NotBlank
    @Length(max = 10)
    private String zipCode;
}
