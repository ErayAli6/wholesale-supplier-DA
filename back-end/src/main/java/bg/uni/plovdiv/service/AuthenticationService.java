package bg.uni.plovdiv.service;

import bg.uni.plovdiv.model.security.*;
import bg.uni.plovdiv.repository.UserRepository;
import bg.uni.plovdiv.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(createAdmin());
        }
    }


    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(request.getUserRole())
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public String remove(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        userRepository.delete(user);
        return username + " was removed!";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createAdmin() {
        return User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .firstName("admin")
                .lastName("admin")
                .userRole(UserRole.ADMIN)
                .build();
    }
}
