package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.CompanyService;
import bg.uni.plovdiv.service.TokenService;
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
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    private final TokenService tokenService;

    @GetMapping
    public String company() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "company/company";
    }

    @GetMapping("/get-all")
    public String companyAllCompany() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "company/all-company";
    }

    @GetMapping("/get-by-bulstat")
    public String companyByBulstat() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "company/company-by-bulstat";
    }

    @GetMapping("/remove")
    public String removeCompany() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "company/company-remove";
    }

    @GetMapping("/add")
    public String registerCompany() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "company/add-company";
    }

    @GetMapping("/edit")
    public String editCompany() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "company/edit-company";
    }

    @PostMapping("/get-all")
    public String getAllCompanies(Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String allCompanies = companyService.getCompanies(tokenService.getToken());
        model.addAttribute("allCompanies", allCompanies);
        return "company/all-company";
    }

    @PostMapping("/get-by-bulstat")
    public String getCompanyByBulstat(@NotBlank @Length(max = 35) @RequestParam String bulstat, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String companyByBulstat = companyService.getCompanyByBulstat(bulstat, tokenService.getToken());
        model.addAttribute("companyByBulstat", companyByBulstat);
        return "company/company-by-bulstat";
    }

    @PostMapping("/remove")
    public String removeCompany(@NotBlank @Length(max = 35) @RequestParam String bulstat, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String removeCompany = companyService.removeCompany(bulstat, tokenService.getToken());
        model.addAttribute("removeCompany", removeCompany);
        return "company/company-remove";
    }

    @PostMapping("/add")
    public String addCompany(@NotBlank @Length(max = 45) @RequestParam String bulstat, @RequestParam @Length(max = 35) String name,
                             @NotBlank @RequestParam @Length(max = 30) String country, @NotBlank @RequestParam @Length(max = 30) String state,
                             @NotBlank @RequestParam @Length(max = 30) String city, @NotBlank @RequestParam @Length(max = 30) String street,
                             @NotBlank @RequestParam @Length(max = 30) String number, @NotBlank @RequestParam @Length(max = 30) String zipCode,
                             @NotBlank @RequestParam @Length(max = 20) String vatNumber, @RequestParam @Length(max = 15) String phoneNumber,
                             @RequestParam @Length(max = 35) String email, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String addCompany = companyService.addCompany(bulstat, name, country, state, city, street, number, zipCode, vatNumber, phoneNumber, email, tokenService.getToken());
        model.addAttribute("addCompany", addCompany);
        return "company/add-company";
    }

    @PostMapping("/edit")
    public String editCompany(@NotBlank @Length(max = 45) @RequestParam String bulstat, @RequestParam @Length(max = 35) String name,
                              @NotBlank @RequestParam @Length(max = 30) String country, @NotBlank @RequestParam @Length(max = 30) String state,
                              @NotBlank @RequestParam @Length(max = 30) String city, @NotBlank @RequestParam @Length(max = 30) String street,
                              @NotBlank @RequestParam @Length(max = 30) String number, @NotBlank @RequestParam @Length(max = 30) String zipCode,
                              @NotBlank @RequestParam @Length(max = 20) String vatNumber, @RequestParam @Length(max = 15) String phoneNumber,
                              @RequestParam @Length(max = 35) String email, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String editCompany = companyService.editCompany(bulstat, name, country, state, city, street, number, zipCode, vatNumber, phoneNumber, email, tokenService.getToken());
        model.addAttribute("editCompany", editCompany);
        return "company/edit-company";
    }
}