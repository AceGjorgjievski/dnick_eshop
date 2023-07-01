package mk.ukim.finki.dnick.bootstrap;

import lombok.Getter;
import mk.ukim.finki.dnick.model.Brand;
import mk.ukim.finki.dnick.model.User;
import mk.ukim.finki.dnick.model.enums.Role;
import mk.ukim.finki.dnick.repository.jpa.BrandRepository;
import mk.ukim.finki.dnick.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {

    public static List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final PasswordEncoder passwordEncoder;

    public DataHolder(UserRepository userRepository,
                      BrandRepository brandRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        if(this.brandRepository.findAll().size() == 0) {
            List<Brand> brands = List.of(
                    new Brand("Apple", "Apple-description"),
                    new Brand("Google", "Google-description"),
                    new Brand("Microsoft", "Microsoft-description"),
                    new Brand("Samsung", "Samsung-description"),
                    new Brand("Intel", "Intel-description"),
                    new Brand("AMD", "AMD-description"),
                    new Brand("Dell", "Dell-description")
            );

            this.brandRepository.saveAll(brands);
        }
    }
}
