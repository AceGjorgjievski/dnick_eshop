package mk.ukim.finki.dnick.service.impl;

import mk.ukim.finki.dnick.model.Order;
import mk.ukim.finki.dnick.model.Product;
import mk.ukim.finki.dnick.model.ShoppingCartItem;
import mk.ukim.finki.dnick.repository.jpa.OrderRepository;
import mk.ukim.finki.dnick.repository.jpa.ProductRepository;
import mk.ukim.finki.dnick.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public void placeOrder(List<ShoppingCartItem> shoppingCartItems, String address) {

        Order order = new Order(shoppingCartItems, address);

        for(ShoppingCartItem i : shoppingCartItems) {
            Product product = this.productRepository.findById(i.getProduct().getId()).get();
            product.setQuantity(0);
            this.productRepository.save(product);
        }

        this.orderRepository.save(order);

    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }
}
