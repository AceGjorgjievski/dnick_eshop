package mk.ukim.finki.dnick.repository.jpa;


import mk.ukim.finki.dnick.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

//@Repository
//@Transactional
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
}
