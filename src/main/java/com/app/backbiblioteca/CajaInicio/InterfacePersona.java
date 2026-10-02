package com.app.backbiblioteca.CajaInicio;

import org.springframework.data.jpa;

public interface InterfacePersona extends JpaRepository<Persona, String>{

boolean existsByNombre(String nombre);




}