package mk.ukim.finki.dnick.repository.jpa;

import mk.ukim.finki.dnick.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByNameContaining(String name);
}
