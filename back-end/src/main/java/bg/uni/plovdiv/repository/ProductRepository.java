package bg.uni.plovdiv.repository;

import bg.uni.plovdiv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByBarcode(String barcode);
}
