package mk.ukim.finki.dnick.web;

import mk.ukim.finki.dnick.model.enums.Role;
import mk.ukim.finki.dnick.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("hasError", true);
        model.addAttribute("errorMsg", error);
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            LocalDate birthday = LocalDate.parse(request.getParameter("bdate"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String passwordRepeat = request.getParameter("password-repeat");
            Role userRole = Role.USER;

            this.userService.register(name, surname, birthday, email, username, password, passwordRepeat, userRole);

            return "redirect:/login?SuccessfullyRegistered";
        } catch (Exception e) {
            return "redirect:/register?error="+e.getMessage();
        }
    }
}