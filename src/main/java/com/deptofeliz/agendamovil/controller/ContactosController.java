package com.deptofeliz.agendamovil.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deptofeliz.agendamovil.service.ITokenService;
import com.deptofeliz.agendamovil.service.IUsuarioService;
import com.deptofeliz.agendamovil.service.impl.GoogleContactsService;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

@Controller
@RequestMapping("/contactos")
public class ContactosController {

    private final GoogleContactsService googleContactsService;
    private final IUsuarioService usuarioService;
    private final ITokenService tokenService;

    public ContactosController(GoogleContactsService googleContactsService, IUsuarioService usuarioService, ITokenService tokenService) {
        this.googleContactsService = googleContactsService;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public String mostrarContactos(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
                                   Authentication authentication) throws GeneralSecurityException, IOException {

        try {
            // Obtener el accessToken utilizando el servicio de tokens
            String accessToken = tokenService.obtenerToken(authentication);

            // Obtener contactos de Google
            ListConnectionsResponse contactosResponse = googleContactsService.obtenerContactos(accessToken);

            // Paginación
            int pageSize = 10;
            List<Person> contactos = contactosResponse.getConnections();
            int totalPages = (int) Math.ceil((double) contactos.size() / pageSize);
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, contactos.size());

            List<Person> paginaContactos = contactos.subList(startIndex, endIndex);

            model.addAttribute("contactos", paginaContactos);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);

            return "contactos";
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 403) {
                model.addAttribute("error", "Acceso denegado: " + e.getDetails().getMessage());
                return "error";
            } else {
                throw new RuntimeException("Error al obtener los contactos de Google", e);
            }
        }
    }
}
