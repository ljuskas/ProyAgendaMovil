package com.deptofeliz.agendamovil.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deptofeliz.agendamovil.model.entity.Usuario;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, UUID>{
    Optional<Usuario> findByEmail(String email);
}
