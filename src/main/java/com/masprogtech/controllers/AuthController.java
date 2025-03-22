package com.masprogtech.controllers;


import com.masprogtech.dtos.CategoryDTO;
import com.masprogtech.entities.User;
import com.masprogtech.security.dtos.AuthRequest;
import com.masprogtech.security.dtos.AuthResponse;
import com.masprogtech.security.jwt.JwtUtil;
import com.masprogtech.services.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints para registar e fazer login")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final AuthService authService;


    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Registrar usuário", description = "Registrar um usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário registado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        User user = authService.register(authRequest.getName(), authRequest.getTelephone(),
                authRequest.getUsername(), authRequest.getPassword(), authRequest.getRole());
        return ResponseEntity.ok("Usuário registrado com sucesso: " + user.getUsername());
    }

    @Operation(summary = "Efectuar login", description = "login efectuado com usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário registado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities().iterator().next().getAuthority());
        User user = authService.findByUsername(authRequest.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getName(), user.getUsername(), user.getRole()));
    }

}
