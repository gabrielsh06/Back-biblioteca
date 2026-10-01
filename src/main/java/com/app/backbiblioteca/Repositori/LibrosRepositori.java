package com.app.backbiblioteca.Repositori;

import com.app.backbiblioteca.entidades.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrosRepositori extends JpaRepository<Libros, String> {
    boolean existsByNombre(String nombre);

}
