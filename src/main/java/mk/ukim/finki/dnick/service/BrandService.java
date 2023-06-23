package mk.ukim.finki.dnick.service;


import mk.ukim.finki.dnick.model.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> listAll();
    Brand findById(Long id);

    void deleteById(Long id);
    Brand create(String name, String description);
    Brand edit(Long brandId, String name, String description);

}
