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
	
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
}