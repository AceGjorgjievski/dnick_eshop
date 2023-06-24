package mk.ukim.finki.dnick.service;

import mk.ukim.finki.dnick.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product add(String name, MultipartFile multipartFile,
                Integer quantity, Double price, Long brandId);

    Product add(String name, String image,
                Integer quantity, Double price, Long brandId);

    List<Product> listAll();

    Product findById(Long id);

    void deleteById(Long id);

    Product edit(Long productId, String name, MultipartFile file,
                 Integer quantity, Double price, Long brandId);

    Product edit(Long productId, String name, String image,
                 Integer quantity, Double price, Long brandId);

    List<Product> filter(String productName, String productBrand, Double productPrice);

    List<Product> filter(String productName, Long productBrand, Double productPrice);

    void save(Product p);
}
