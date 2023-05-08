package bg.uni.plovdiv.service;

import bg.uni.plovdiv.dto.CompanyDTO;
import bg.uni.plovdiv.model.Company;
import bg.uni.plovdiv.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(company ->
                        CompanyDTO.builder()
                                .bulstat(company.getBulstat())
                                .name(company.getName())
                                .address(company.getAddress())
                                .vatNumber(company.getVatNumber())
                                .phoneNumber(company.getPhoneNumber())
                                .email(company.getEmail())
                                .build())
                .toList();
    }

    public Optional<CompanyDTO> getCompanyByBulstat(String bulstat) {
        Company company = companyRepository.findByBulstat(bulstat);
        if (company == null) {
            return Optional.empty();
        }
        return Optional.of(CompanyDTO.builder()
                .bulstat(company.getBulstat())
                .name(company.getName())
                .address(company.getAddress())
                .vatNumber(company.getVatNumber())
                .phoneNumber(company.getPhoneNumber())
                .email(company.getEmail())
                .build());
    }

    public boolean registerCompany(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .bulstat(companyDTO.getBulstat())
                .name(companyDTO.getName())
                .address(companyDTO.getAddress())
                .vatNumber(companyDTO.getVatNumber())
                .phoneNumber(companyDTO.getPhoneNumber())
                .email(companyDTO.getEmail())
                .build();
        try {
            companyRepository.save(company);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public boolean editCompany(CompanyDTO companyDTO) {
        Company company = companyRepository.findByBulstat(companyDTO.getBulstat());
        if (company == null) {
            return false;
        }
        company.setBulstat(companyDTO.getBulstat());
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setVatNumber(companyDTO.getVatNumber());
        company.setPhoneNumber(companyDTO.getPhoneNumber());
        company.setEmail(companyDTO.getEmail());
        try {
            companyRepository.save(company);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    public boolean removeCompany(String bulstat) {
        Company company = companyRepository.findByBulstat(bulstat);
        if (company == null) {
            return false;
        }
        try {
            companyRepository.delete(company);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
