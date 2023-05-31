package bg.uni.plovdiv.controller;

import bg.uni.plovdiv.model.security.AuthenticationRequest;
import bg.uni.plovdiv.model.security.AuthenticationResponse;
import bg.uni.plovdiv.model.security.RegisterRequest;
import bg.uni.plovdiv.model.security.User;
import bg.uni.plovdiv.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", security = @SecurityRequirement(name = "bearerAuth"))
    public AuthenticationResponse register(@RequestBody RegisterRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user credentials")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove a user by username", security = @SecurityRequirement(name = "bearerAuth"))
    public String remove(@RequestParam @NotBlank String username) {
        return authenticationService.remove(username);
    }

    @GetMapping("/getUsers")
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    public List<User> getUsers() {
        return authenticationService.getAllUsers();
    }
}
