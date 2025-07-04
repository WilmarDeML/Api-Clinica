package com.wilmardeml.apimed.infra.seguridad;

import com.wilmardeml.apimed.repositorios.UsuarioRepository;
import com.wilmardeml.apimed.servicios.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public FilterSecurity(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var JWToken = recuperarToken(request);

        if (JWToken != null) {
            var username = tokenService.obtenerUsernameDelToken(JWToken);
            var usuarioBuscado = repository.findByUsername(username);
            var autenticacion = new UsernamePasswordAuthenticationToken(
                    usuarioBuscado, null, usuarioBuscado.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationToken = request.getHeader("Authorization");
        if (authorizationToken != null)
            return authorizationToken.split(" ")[1];

        return null;
    }
}
