package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.dto.CompanyDTO;
import bg.uni.plovdiv.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/company")
@Tag(name = "Company endpoints")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "Get all companies")
    public List<CompanyDTO> getCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{bulstat}")
    @Operation(summary = "Get a company by bulstat")
    public Optional<CompanyDTO> getCompanyByBulstat(@NotBlank @Length(max = 45) @RequestParam String bulstat) {
        return companyService.getCompanyByBulstat(bulstat);
    }

    @PostMapping
    @Operation(summary = "Register a new company")
    public boolean registerCompany(@NotNull @Valid @RequestBody CompanyDTO companyDTO) {
        return companyService.registerCompany(companyDTO);
    }

    @PutMapping
    @Operation(summary = "Edit an existing company")
    public boolean editCompany(@NotNull @Valid @RequestBody CompanyDTO companyDTO) {
        return companyService.editCompany(companyDTO);
    }

    @DeleteMapping("/{bulstat}")
    @Operation(summary = "Remove a company by bulstat")
    public boolean removeCompany(@NotBlank @Length(max = 45) @RequestParam String bulstat) {
        return companyService.removeCompany(bulstat);
    }
}
