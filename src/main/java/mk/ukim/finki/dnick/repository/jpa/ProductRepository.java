package mk.ukim.finki.dnick.repository.jpa;


import mk.ukim.finki.dnick.model.Brand;
import mk.ukim.finki.dnick.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    List<Product> findAllByNameLike(String productName);
    List<Product> findAllByNameLikeAndBrand(String productName, Brand brand);
    List<Product> findAllByNameLikeAndBrandAndPriceLessThanEqual(String productName, Brand brand, Double productPrice);

    List<Product> findAllByBrand(Brand brand);
    List<Product> findAllByBrandAndPriceLessThanEqual(Brand brand, Double productPrice);
    List<Product> findAllByNameLikeAndPriceLessThanEqual(String productName, Double productPrice);
    List<Product> findAllByPriceLessThanEqual(Double productPrice);
}
