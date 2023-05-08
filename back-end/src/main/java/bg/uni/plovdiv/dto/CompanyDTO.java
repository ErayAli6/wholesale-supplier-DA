package bg.uni.plovdiv.dto;

import bg.uni.plovdiv.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
public class CompanyDTO {

    @NotBlank
    @Length(max = 45)
    private String bulstat;

    @Length(max = 35)
    private String name;

    @Valid
    private Address address;

    @NotBlank
    @Length(max = 20)
    private String vatNumber;

    @Length(max = 15)
    private String phoneNumber;

    @Email
    @Length(max = 35)
    private String email;
}
