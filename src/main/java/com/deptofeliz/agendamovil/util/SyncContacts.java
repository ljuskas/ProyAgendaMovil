package com.deptofeliz.agendamovil.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

public class SyncContacts {

    private static final String APPLICATION_NAME = "Sync Contacts";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();  // Usamos JacksonFactory
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(PeopleServiceScopes.CONTACTS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(SyncContacts.class.getResourceAsStream(CREDENTIALS_FILE_PATH)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String[] args) throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses")
                .execute();
        List<Person> connections = response.getConnections();

        if (connections != null && !connections.isEmpty()) {
            for (Person person : connections) {
                if (person.getNames() != null && !person.getNames().isEmpty()) {
                    System.out.println("Name: " + person.getNames().get(0).getDisplayName());
                }
                if (person.getEmailAddresses() != null && !person.getEmailAddresses().isEmpty()) {
                    System.out.println("Email: " + person.getEmailAddresses().get(0).getValue());
                }
            }
        }
    }
}

