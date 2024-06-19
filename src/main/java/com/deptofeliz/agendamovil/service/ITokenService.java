package com.deptofeliz.agendamovil.service;

import org.springframework.security.core.Authentication;

public interface ITokenService {
    String obtenerToken(Authentication authentication);
}
