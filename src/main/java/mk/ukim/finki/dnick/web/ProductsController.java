package mk.ukim.finki.dnick.web;


import mk.ukim.finki.dnick.model.Product;
import mk.ukim.finki.dnick.service.BrandService;
import mk.ukim.finki.dnick.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final BrandService brandService;


    public ProductsController(ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping
    public String getProductsPage(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productBrand,
            @RequestParam(required = false) Double productPrice,
//            @RequestParam(required = false) Long productBrand,
            Model model) {
        List<Product> productList = null;
        Long productBrandId = null;

        if(productName == null && productBrand == null && productPrice == null) {
            productList = this.productService.listAll();
        } else {
            if(productBrand.length() > 1) { // productBrand = ",1" or ",2" idk why .. List<Long> ?
                productBrandId = Long.parseLong(String.valueOf(productBrand.charAt(1)));
            } else if(productBrand.length() == 1 && Character.isDigit(productBrand.charAt(0))){
                productBrandId = Long.parseLong(productBrand);
            } else {
                productBrandId = null;
            }
            productList = this.productService.filter(productName, productBrandId, productPrice);
        }

        productList = productList.stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());

        model.addAttribute("productsList", productList);
        model.addAttribute("brands", this.brandService.listAll());
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("brands", this.brandService.listAll());
        model.addAttribute("addNewProduct", true);
        model.addAttribute("bodyContent", "addProduct");
        return "master-template";
    }

    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model) {

        Product p = this.productService.findById(id);
        model.addAttribute("product", p);
        model.addAttribute("brands", this.brandService.listAll());
        model.addAttribute("addNewProduct", false);
        model.addAttribute("bodyContent", "addProduct");

        return "master-template";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam(required = false) Long id,
                             @RequestParam("name") String name,
//                             @RequestParam("myfile") MultipartFile file,
                             @RequestParam String image,
                             @RequestParam("quantity") Integer quantity,
                             @RequestParam("price") Double price,
                             @RequestParam("brandId") Long brandId) {
//        this.productService.add(name,file,price,brandId);
        if(id == null) {
            this.productService.add(name,image,quantity, price,brandId);
            return "redirect:/products?SuccessfullyAdded";
        } else {
            this.productService.edit(id, name, image, quantity, price, brandId);
            return "redirect:/products?SuccessfullyModified";
        }
    }


}
