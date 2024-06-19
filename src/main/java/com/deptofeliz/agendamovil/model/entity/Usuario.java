package com.deptofeliz.agendamovil.model.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "uuid", columnDefinition = "uuid", updatable = false)
    private UUID    uuid;
    private String  nombre;
    private String  email;
    private String  token;
    private Date    fechaIngreso;
}
