package mk.ukim.finki.dnick.model;


import lombok.Data;
import mk.ukim.finki.dnick.model.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "shop_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    private String password;

    private String username;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<ShoppingCart> carts;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(String name,
                String surname,
                LocalDate birthday,
                String username,
                String email,
                String password,
                Role role) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", role=" + role +
                '}';
    }
}
