package com.deptofeliz.agendamovil.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deptofeliz.agendamovil.model.entity.Usuario;
import com.deptofeliz.agendamovil.repository.IUsuarioRepository;
import com.deptofeliz.agendamovil.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    private IUsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void guardar(Usuario objUsuario) {
        Usuario usuario = obtenerPorEmail(objUsuario);
        if(usuario.getToken() == null || !objUsuario.getToken().equals(usuario.getToken())) {
            usuario.setToken(objUsuario.getToken());
            usuario.setFechaIngreso(objUsuario.getFechaIngreso());
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerPorEmail(Usuario objUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(objUsuario.getEmail());
        if(usuarioOptional.isPresent()){
            return usuarioOptional.get();
        }
        return objUsuario;
    }

}
