package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.AuthenticationService;
import bg.uni.plovdiv.service.TokenService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final TokenService tokenService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/getUsers")
    public String allUsers() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "auth/all-users";
    }

    @GetMapping("/remove")
    public String removeUser() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "auth/remove";
    }

    @GetMapping("/register")
    public String register() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "auth/register";
    }

    @GetMapping("/logout")
    public String logout() {
        tokenService.setToken(null);
        return "auth/login";
    }

    @GetMapping("/admin-panel")
    public String admin() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "auth/admin-panel";
    }

    @PostMapping("/login")
    public String authenticate(@NotBlank String username, @NotBlank String password) {
        String token = authenticationService.authenticate(username, password);
        tokenService.setToken(token);
        String response = null;
        if (tokenService.getToken() != null) {
            response = "index";
        } else {
            response = "auth/login";
        }
        return response;
    }

    @PostMapping("/remove")
    public String remove(@NotBlank String username, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String response = authenticationService.remove(username, tokenService.getToken());
        model.addAttribute("response", response);
        return "auth/remove";
    }

    @PostMapping("/register")
    public String register(@NotBlank String username, @NotBlank String password, @NotBlank String firstName, @NotBlank String lastName, @NotBlank String userRole, Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String response = authenticationService.register(username, password, firstName, lastName, userRole, tokenService.getToken());
        model.addAttribute("response", response);
        return "auth/register";
    }

    @PostMapping("/getUsers")
    public String getUsers(Model model) {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        String allUsers = authenticationService.getUsers(tokenService.getToken());
        model.addAttribute("allUsers", allUsers);
        return "auth/all-users";
    }
}
