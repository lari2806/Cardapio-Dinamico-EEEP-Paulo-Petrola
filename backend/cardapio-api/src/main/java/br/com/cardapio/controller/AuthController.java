package br.com.cardapio.controller;

import br.com.cardapio.dto.LoginRequest;
import br.com.cardapio.dto.RegisterRequest;
import br.com.cardapio.dto.TokenResponse;
import br.com.cardapio.model.User;
import br.com.cardapio.service.AuthService;
import br.com.cardapio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // LOGIN usando LoginRequest DTO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    // REGISTER usando RegisterRequest DTO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest dto) {

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        User created = userService.create(user);
        return ResponseEntity.ok(created);
    }
}
