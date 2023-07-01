package mk.ukim.finki.dnick.repository.jpa;


import mk.ukim.finki.dnick.model.Brand;
import mk.ukim.finki.dnick.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByNameContaining(String productName);
//    List<Product> findAllByNameLike(String productName);
    List<Product> findProductsByBrand(Brand brand);
    List<Product> findProductsByPrice(Double productPrice);

    List<Product> findProductsByNameContainingAndBrand(String productName, Brand brand);
    List<Product> findProductsByNameContainingAndPrice(String productName, Double productPrice);
    List<Product> findProductsByPriceAndBrand(Double productPrice, Brand brand);

    List<Product> findProductsByNameContainingAndBrandAndPrice(String productName,
                                                               Brand brand,
                                                               Double productPrice);

    List<Product> findAllByNameLikeIgnoreCaseAndBrandAndPriceLessThanEqual(String productName, Brand brand, Double productPrice);
    List<Product> findAllByNameLikeIgnoreCase(String productName);
    List<Product> findAllByNameLikeIgnoreCaseAndBrand(String productName, Brand brand);

    List<Product> findAllByBrand(Brand brand);
    List<Product> findAllByBrandAndPriceLessThanEqual(Brand brand, Double productPrice);
    List<Product> findAllByNameLikeIgnoreCaseAndPriceLessThanEqual(String productName, Double productPrice);
    List<Product> findAllByPriceLessThanEqual(Double productPrice);
}
