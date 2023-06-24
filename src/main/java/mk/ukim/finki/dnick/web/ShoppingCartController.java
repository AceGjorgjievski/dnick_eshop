package mk.ukim.finki.dnick.web;


import mk.ukim.finki.dnick.model.ShoppingCart;
import mk.ukim.finki.dnick.model.ShoppingCartItem;
import mk.ukim.finki.dnick.model.User;
import mk.ukim.finki.dnick.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String getShoppingCartPage(Model model, HttpServletRequest request) {

        String username = request.getRemoteUser();
        ShoppingCart cart = null;
        List<ShoppingCartItem> cartItemList = null;


        cart = this.shoppingCartService.getActiveCart(username);
        cartItemList = this.shoppingCartService.listAllShoppingCartItems(cart.getId());


        double totalPrice = cartItemList.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItemsList", cartItemList);
        model.addAttribute("bodyContent", "shoppingCart");
        return "master-template";
    }

    @PostMapping("/{id}/add-product")
    public String addProductToShoppingCart(@PathVariable Long id,
                                           HttpServletRequest req) {
        String username = req.getRemoteUser();
        boolean addedToShoppingCart = this.shoppingCartService.addProductToShoppingCart(username, id);
        String text = null;
        if(addedToShoppingCart) text = "SuccessfullyAddedToCart";
        else text = "NotAddedInCart";

        return "redirect:/products?"+text;
    }

    @DeleteMapping("/{id}/remove")
    public String deleteShoppingCatItem(@PathVariable Long id,
                                        HttpServletRequest req) {
        String username = req.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveCart(username);
        this.shoppingCartService.removeProductFromShoppingCart(shoppingCart, id);
        return "redirect:/cart?SuccessfullyRemovedFromCart";
    }

}
