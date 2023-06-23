package mk.ukim.finki.dnick.web;


import mk.ukim.finki.dnick.model.Order;
import mk.ukim.finki.dnick.model.ShoppingCart;
import mk.ukim.finki.dnick.model.ShoppingCartItem;
import mk.ukim.finki.dnick.service.OrderService;
import mk.ukim.finki.dnick.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService,
                           ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getOrderPage(Model model) {


        model.addAttribute("orders", this.orderService.findAll());
        model.addAttribute("bodyContent", "order");

        return "master-template";
    }

    @PostMapping("/prepareOrder")
    public String prepareOrder(Model model,HttpServletRequest request,
                               @RequestParam String address) {

        String username = request.getRemoteUser();
        ShoppingCart cart = this.shoppingCartService.getActiveCart(username);

        List<ShoppingCartItem> shoppingCartItems = this.shoppingCartService.listAllShoppingCartItems(cart.getId());

        this.orderService.placeOrder(shoppingCartItems, address); //error here
        this.shoppingCartService.emptyShoppingCart(username);
        return "redirect:/order";
    }
}
