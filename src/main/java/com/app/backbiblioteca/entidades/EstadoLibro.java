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
private int estado;

    public EstadoLibro() {
    }

    public EstadoLibro(Libros libros, int estado, String idEstado) {
        this.libros = libros;
        this.estado = estado;
        this.idEstado = idEstado;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public Libros getLibros() {
        return libros;
    }

    public void setLibros(Libros libros) {
        this.libros = libros;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
