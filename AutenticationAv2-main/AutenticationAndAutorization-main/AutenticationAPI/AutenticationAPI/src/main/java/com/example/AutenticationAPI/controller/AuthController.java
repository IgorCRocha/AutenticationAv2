package com.example.AutenticationAPI.controller;

import com.example.AutenticationAPI.model.LoginRequest;
import com.example.AutenticationAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // Aqui você pode autenticar o usuário (por exemplo, usando um banco de dados)
        // Se a autenticação for bem-sucedida, gere um token JWT

        // String token = JwtUtil.generateToken(request.getUsername());
        // Ao invés de chamarmos JwtUtil diretamente, criamos uma camada de serviço
        String token = authService.generateToken(request.getUsername());
        return token;
    }
    // No login, capturamos o Username via corpo da requisição (LoginRequest Body)
    // Em seguida, geramos um token JWT

    @GetMapping("/username/{token}")
    public String extractUsername(@PathVariable String token) {
        // String username = JwtUtil.extractUsername(token);
        // Ao invés de chamarmos JwtUtil diretamente, utilizamos a camada de serviço
        String username = authService.extractUsername(token);
        return username;
    }
    // No extractUsername, capturamos o token via URL apenas por praticidade
    // (outra opção de obter é via @RequestBody )
    // Em seguida, extraímos o username deste token

    // Qualquer um pode acessar
    @GetMapping("/user")
    public String getUser(Authentication authentication) {
        return "User: " + authentication.getName();
    }

    // Somente o admin pode acessar
    @Secured("ADMIN")
    @GetMapping("/admin")
    public String onlyAdmin(Authentication authentication) {
        return "Admin: " + authentication.getName();
    }

     @Secured("GERENTE")
    @GetMapping("/manager/products")
    public String onlyGerente(Authentication authentication) {
        return "Gerente: " + authentication.getName();
    }


    @GetMapping("/seller/orders")
    public String onlyVendedorCliente(Authentication authentication) {
        return "Vendedor: " + authentication.getName();
    }

    @GetMapping("/customer/products")
    public String onlyCliente(Authentication authentication) {
        return "Cliente: " + authentication.getName();
    }
}

