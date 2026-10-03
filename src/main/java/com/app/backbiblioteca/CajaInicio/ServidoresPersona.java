package com.app.backbiblioteca.CajaInicio;
 import org.springframework.stereotype.Service;

@Service
public class ServidoresPersona{
private final InterfacePersona interper;
	
	public ServidoresPersona(InterfacePersona interper){
	this.interper = interper;
	
	}


	public Persona crear(dtopersona dto){
	Persona persona = interper.findById(dto.getIdpersona())
	.orElseThrow(() -> new RuntimeException("Persona no encontrada"));
	
	Persona personadb = new Persona();
		personadb.setNombre(dto.getNombre);
		personadb.setIdpersona(dto.getIdpersona());
		personadb.setContrasenna(dto.getContrasenna);
		personadb.setDetalles(dto.getDetalles());
	
	return interper.save(personadb);
	
	}
	
	
	
	
	
	
	
	
	
}