package com.app.backbiblioteca.CajaInicio;
import jakarta.persistence.validation.constraints.*;


@RequestController
@RequestMapping("/cnt/persona")
public class Controlladorp{
private final ServidoresPersona persev;
	public Controlladorp(ServidoresPersona persev){this.persev = persev;}

@PostMapping
public ResponseEntity<Producto> crear(
@Valid @RequestBody dtopersona dtoper) {
Persona creado = persev.crear(dtoper);
return ResponseEntity.status(HttpStatus.CREATED).body(creado);
}

	
	


}