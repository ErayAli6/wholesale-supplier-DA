package bg.uni.plovdiv.repository;

import bg.uni.plovdiv.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByBulstat(String bulstat);
}
