package mk.ukim.finki.dnick.service.impl;

import mk.ukim.finki.dnick.model.Brand;
import mk.ukim.finki.dnick.model.exceptions.BrandNotFoundException;
import mk.ukim.finki.dnick.repository.jpa.BrandRepository;
import mk.ukim.finki.dnick.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<Brand> listAll() {
        return this.brandRepository.findAll();
    }

    @Override
    public Brand create(String name, String description) {
        Brand b = new Brand(name, description);
        return this.brandRepository.save(b);
    }

    @Override
    public Brand findById(Long id) {
        return this.brandRepository
                .findById(id)
                .orElseThrow(()->new BrandNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        this.brandRepository.deleteById(id);
    }

    @Override
    public Brand edit(Long brandId, String name, String description) {
        Brand b = this.findById(brandId);

        b.setName(name);
        b.setDescription(description);

        return this.brandRepository.save(b);
    }
}
