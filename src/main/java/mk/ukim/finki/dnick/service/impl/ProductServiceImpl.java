package mk.ukim.finki.dnick.service.impl;


import mk.ukim.finki.dnick.model.Brand;
import mk.ukim.finki.dnick.model.Product;
import mk.ukim.finki.dnick.model.exceptions.BrandNotFoundException;
import mk.ukim.finki.dnick.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.dnick.repository.jpa.BrandRepository;
import mk.ukim.finki.dnick.repository.jpa.ProductRepository;
import mk.ukim.finki.dnick.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }


    @Override
    public List<Product> listAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product add(String name, MultipartFile multipartFile, Integer quantity, Double price, Long brandId) {

        String image = this.setProductImage(multipartFile);
//        System.out.println("Adding an image: "+image);

        Brand b = this.brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));


        Product p = new Product(name, image, quantity, price, b);
        return this.productRepository.save(p);
    }

    @Override
    public Product add(String name, String image, Integer quantity, Double price, Long brandId) {
        Brand b = this.brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        Product p = new Product(name, image, quantity, price, b);

        return this.productRepository.save(p);
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Product edit(Long productId, String name, MultipartFile file,Integer quantity, Double price, Long brandId) {
        Product p = this.findById(productId);

        p.setName(name);

        String image = this.setProductImage(file);
        p.setQuantity(quantity);
        p.setImage(image);
        p.setPrice(price);

        Brand b = this.brandRepository
                .findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        p.setBrand(b);

        return this.productRepository.save(p);
    }

    @Override
    public Product edit(Long productId, String name, String image, Integer quantity, Double price, Long brandId) {
        Product p = this.findById(productId);

        p.setName(name);
        p.setImage(image);
        p.setQuantity(quantity);
        p.setPrice(price);

        Brand b = this.brandRepository
                .findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        p.setBrand(b);
        return this.productRepository.save(p);
    }

    @Override
    public List<Product> filter(String productName, String productBrand, Double productPrice) {

        Brand brand = productBrand != null && !productBrand.isEmpty() ?
                this.brandRepository.findByNameContaining(productBrand) : null;


        if (productName != null && !productName.isEmpty() && brand != null && productPrice != null) {
            return this.productRepository
                    .findProductsByNameContainingAndBrandAndPrice(productName, brand, productPrice);
        } else if (!productName.isEmpty() && brand != null && productPrice == null) {
            return this.productRepository
                    .findProductsByNameContainingAndBrand(productName, brand);
        } else if (!productName.isEmpty() && productPrice != null && brand == null) {
            return this.productRepository
                    .findProductsByNameContainingAndPrice(productName, productPrice);
        } else if (brand != null && productPrice != null && productName.isEmpty()) {
            return this.productRepository
                    .findProductsByPriceAndBrand(productPrice, brand);
        } else if (!productName.isEmpty() && brand == null && productPrice == null) {
            return this.productRepository
                    .findProductsByNameContaining(productName);
        } else if (productPrice != null && productName.isEmpty() && brand == null) {
            return this.productRepository
                    .findProductsByPrice(productPrice);
        } else if (brand != null && productName.isEmpty() && productPrice == null) {
            return this.productRepository
                    .findProductsByBrand(brand);
        } else {
            return this.productRepository
                    .findAll();
        }
    }

    @Override
    @Transactional
    public List<Product> filter(String productName, Long productBrandId, Double productPrice) {
        Brand brand = productBrandId != null ? this.brandRepository.findById(productBrandId).orElse((Brand)null)
                : null;
        String nameLike = "%"+productName+"%";
        boolean validProductName = productName != null && !productName.isEmpty();


        if(validProductName && brand == null && productPrice == null) { //ok
            return this.productRepository //samo ime
                    .findAllByNameLike(nameLike);
        } else if(validProductName && brand != null && productPrice == null) { // ok
            return this.productRepository //ime i brand
                    .findAllByNameLikeAndBrand(nameLike, brand);
        } else if (validProductName && brand != null && productPrice != null) {
            return this.productRepository // ime, brand, price
                    .findAllByNameLikeAndBrandAndPriceLessThanEqual(nameLike, brand, productPrice);
        } else if(!validProductName && brand != null && productPrice == null) {
            return this.productRepository  //samo brand
                    .findAllByBrand(brand);
        } else if (!validProductName && brand != null && productPrice != null) {
            //brand i price
            return this.productRepository
                    .findAllByBrandAndPriceLessThanEqual(brand, productPrice);
        } else if (validProductName && brand == null && productPrice != null) {
            //ime, price
            return this.productRepository
                    .findAllByNameLikeAndPriceLessThanEqual(nameLike, productPrice);
        } else if(!validProductName && brand == null && productPrice != null) {
            //price
            return this.productRepository
                    .findAllByPriceLessThanEqual(productPrice);
        }
        return this.productRepository.findAll();

    }

    @Override
    public void save(Product p) {
        this.productRepository.save(p);
    }

    private String setProductImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imageName = null;
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            imageName = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageName;
    }


}
