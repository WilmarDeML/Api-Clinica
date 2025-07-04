package com.wilmardeml.apimed.controladores;

import com.wilmardeml.apimed.modelos.dtos.*;
import com.wilmardeml.apimed.modelos.entidades.Usuario;
import com.wilmardeml.apimed.servicios.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class AutenticacionController {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacionController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> iniciarSesion(@RequestBody @Valid DatosLogin datosLogin) {

        var authToken = new UsernamePasswordAuthenticationToken(datosLogin.username(), datosLogin.pass());
        var autenticacion = manager.authenticate(authToken);

        var JWToken = tokenService.generarToken((Usuario) autenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosJWToken(JWToken));
    }
}
