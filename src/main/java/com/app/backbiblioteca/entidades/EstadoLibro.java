package com.app.backbiblioteca.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "estadolibro")
public class EstadoLibro {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idEstado;

@ManyToOne(optional = false)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libros libros;



}
