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

import java.text.Normalizer;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // ========== LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    // ========== REGISTER ==========
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest dto) {
        try {
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword()); // será encriptada no service

            // NORMALIZA A ROLE (remove acentos, maiúsculo)
            user.setRole(normalizeRole(dto.getRole()));

            User created = userService.create(user);
            return ResponseEntity.ok(created);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar usuário.");
        }
    }


    // ========== NORMALIZA ROLE ==========
    private String normalizeRole(String role) {
        if (role == null) return null;

        String noAccent = Normalizer
                .normalize(role, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // remove acentos

        noAccent = noAccent.trim().toUpperCase(); // caixa alta

        return noAccent;
    }
}
