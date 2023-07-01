package mk.ukim.finki.dnick.repository.jpa;

import mk.ukim.finki.dnick.model.ShoppingCart;
import mk.ukim.finki.dnick.model.User;
import mk.ukim.finki.dnick.model.enums.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

//@Repository
//@Transactional
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndCartStatus(User user, ShoppingCartStatus shoppingCartStatus);
}
