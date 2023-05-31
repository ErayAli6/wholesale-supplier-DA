package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final TokenService tokenService;

    @GetMapping
    public String index() {
        if (tokenService.getToken() == null) {
            return "auth/login";
        }
        return "index";
    }
}
