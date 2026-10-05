package com.app.backbiblioteca.CajaInicio;
import jakarta.persistence.validation.constraints.*;
import java.util.List;

@RequestController
@RequestMapping("/cnt/persona")
public class Controlladorp{
private final ServidoresPersona persev;
	public Controlladorp(ServidoresPersona persev){this.persev = persev;}
//Conexion request inicial 
@PostMapping("/Entrgar/")
public ResponseEntity<Producto> crear(
@Valid @RequestBody dtopersona dtoper) {
Persona creado = persev.crear(dtoper);
return ResponseEntity.status(HttpStatus.CREATED).body(creado);
}
//Por que la mayoria actua como funcion? acaso se puede crear endpoints con //void ?
	@PostMapping("/Listar")
	public List<Persona> listar(){
	return persev.listar();
	}
@PutMapping("/Actualizar")
	public Persona actualizar(@Valid @RequestBody dtopersona dtoper)
	{
	Persona creado = persev.actualizar(dtoper, dtoper.getIdpersona());
return creado;
	
	
	
	}
	
	@DeleteMapping("/Eliminar/{id}")
	public Persona eliminar(@PathVariable String id){
	return persev.eliminar(id);
	
	
	}
	
	
	

}