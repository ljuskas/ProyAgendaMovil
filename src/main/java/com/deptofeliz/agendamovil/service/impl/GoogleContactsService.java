package com.deptofeliz.agendamovil.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
@Service
public class GoogleContactsService {

    private static final String APPLICATION_NAME = "HeralApp";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public ListConnectionsResponse obtenerContactos(String accessToken) throws IOException, GeneralSecurityException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        PeopleService service = new PeopleService.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();

        try {
            ListConnectionsResponse response = service.people().connections().list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers,biographies") // Campos que deseas obtener
                    .setAccessToken(accessToken) // Token de acceso OAuth 2.0
                    .execute();

            return response;
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 403) {
                throw new RuntimeException("Permisos insuficientes. Por favor, asegúrate de que el token tenga los scopes correctos.", e);
            } else {
                throw new RuntimeException("Error al obtener los contactos de Google", e);
            }
        }
    }
}
