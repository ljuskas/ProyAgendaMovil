package com.deptofeliz.agendamovil.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deptofeliz.agendamovil.model.entity.Usuario;
import com.deptofeliz.agendamovil.service.ITokenService;
import com.deptofeliz.agendamovil.service.IUsuarioService;

@RestController
@RequestMapping("/")
public class UsuarioController {


    private final IUsuarioService usuarioService;
    private final ITokenService tokenService;

    public UsuarioController(IUsuarioService usuarioService, ITokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public Object login(Authentication authentication) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String name = oauthToken.getPrincipal().getAttribute("name");
        String email = oauthToken.getPrincipal().getAttribute("email");
        String token = tokenService.obtenerToken(authentication);


        Usuario usuario = new Usuario();
        usuario.setNombre(name);
        usuario.setEmail(email);
        usuario.setToken(token);
        usuario.setFechaIngreso(new Date());
        usuarioService.guardar(usuario);

        return ResponseEntity.ok("Usuario almacenado correctamente en la base de datos.");
    }
}