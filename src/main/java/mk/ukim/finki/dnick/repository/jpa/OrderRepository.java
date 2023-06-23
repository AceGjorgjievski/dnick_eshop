package mk.ukim.finki.dnick.repository.jpa;

import mk.ukim.finki.dnick.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
