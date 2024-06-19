package com.deptofeliz.agendamovil.service;

import com.deptofeliz.agendamovil.model.entity.Usuario;

public interface IUsuarioService {
    void    guardar(Usuario objUsuario);
    Usuario obtenerPorEmail(Usuario objUsuario);
}
