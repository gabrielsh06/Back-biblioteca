package com.app.backbiblioteca.CajaInicio;
 import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ServidoresPersona{
private final InterfacePersona interper;
	
	public ServidoresPersona(InterfacePersona interper){
	this.interper = interper;
	
	}


	public Persona crear(dtopersona dto){
		Persona persona = interper.findById(dtopersona.getIDpersona());
	.orElseThrow(() -> new RuntimeException("Persona no encontrada"));
	
	Persona personadb = new Persona();
		personadb.setNombre(dto.getNombre);
		personadb.setIdpersona(dto.getIdpersona());
		personadb.setContrasenna(dto.getContrasenna);
		personadb.setDetalles(dto.getDetalles());
	
	return interper.save(personadb);
	
	}
	
	
	public List<Persona>  listar(){
	
	return interper.findAll().get();
	}
	
	public Persona eliminar(String id){
	return interper.deleteById(id);
	}
	
	
	public Persona actualizar(dtopersona dto, String id){
		Persona persona = interper.findById(id)
	.orElseThrow(() -> new RuntimeException("Persona no encontrada"));
	
	
		persona.setNombre(dto.getNombre);
		persona.setIdpersona(dto.getIdpersona());
		persona.setContrasenna(dto.getContrasenna);
		persona.setDetalles(dto.getDetalles());
	
		
		
		
		
	return interper.save(persona);
	}
	
	
	
	
}