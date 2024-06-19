package com.deptofeliz.agendamovil.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.deptofeliz.agendamovil.service.ITokenService;

@Service
public class TokenServiceImpl implements ITokenService {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public TokenServiceImpl(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public String obtenerToken(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            String principalName = oauthToken.getName();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);
            
            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

            // Si necesitas más detalles del token, puedes crear un objeto que los contenga
            return accessToken.getTokenValue();
        } else {
            throw new IllegalArgumentException("La variable Authentication no es de tipo OAuth2AuthenticationToken");
        }
    }
}

