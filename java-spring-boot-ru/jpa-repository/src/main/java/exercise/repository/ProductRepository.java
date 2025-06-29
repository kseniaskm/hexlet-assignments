package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceBetweenOrderByPrice(Integer startPrice, Integer endPrice);

    List<Product> findByPriceLessThanOrderByPrice(Integer price);

    List<Product> findByPriceGreaterThanOrderByPrice(Integer price);
    // END
}
