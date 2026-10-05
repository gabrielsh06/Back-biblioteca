package com.app.backbiblioteca.CajaInicio;
import jakarta.persistence.validation.constraints.*;


public class dtopersona{
	
	
		@NotBlank(message="la id es obligatorio")
private String idpersona;
	@NotBlank(message="El nombre es obligatorio")
	private String Nombre;
	
	@NotBlank(message="La contrasenna  es obligatorio")

	
	private String Contrasenna;

	
	
	private String Detalles;
	
	publiic Persona(){}
	public String getNombre(){
	return this.Nombre;
	
	}
	public void setNombre(String Nombre){
	this.Nombre = Nombre;
	}
	
public String getIdpersona(){
return this.idpersona;
	
}
	public void setIdpersona(String idpersona){
	this.idpersona = idpersona;
	}
public String getContrasenna(){
return this.Contrasenna;
}

		public void setContrasenna(String Contrasenna){
		this.Contrasenna =	Contrasenna}
	
	//Enumerar de 1 al veinte 
	public String getDetalles(){
	
	return this.Detalles;
	}
	
	public void setDetalles(){
		this.Detalles = Detalles;
	}




}