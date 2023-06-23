package mk.ukim.finki.dnick.service.impl;

import mk.ukim.finki.dnick.model.Order;
import mk.ukim.finki.dnick.model.ShoppingCartItem;
import mk.ukim.finki.dnick.repository.jpa.OrderRepository;
import mk.ukim.finki.dnick.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    @Transactional
    public void placeOrder(List<ShoppingCartItem> shoppingCartItems, String address) {

        Order order = new Order(shoppingCartItems, address);
        this.orderRepository.save(order);

    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }
}
