package mk.ukim.finki.dnick.web;

import mk.ukim.finki.dnick.model.User;
import mk.ukim.finki.dnick.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("hasError", true);
        model.addAttribute("errorMsg", error);
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    @PostMapping
    public String loginUser(HttpServletRequest request) {
        User user = null;

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            user = this.userService.login(username, password);

            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (Exception e) {
            System.out.println("Login - " + e.getMessage());
            return "redirect:/login?error="+e.getMessage();
        }
    }
}
