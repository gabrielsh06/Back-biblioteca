package com.app.backbiblioteca.Cajitapersona;
import jakarta.persistence.*;
import jakarta.persistence.validation.contraints.*;



@Entity
@Table(name = "user")
public class User{
public User(){}

@Id
private String iduser;


private String name;


private String contrasenna;
private String grado;
private String detalles;
public void getIduser(){

return this.iduser;

}

public String setIduser(String iduser){

this.iduser = iduser;

} 

public void getname(){
return this.name;

}

public void setname(String name){
this.name = name;
}

public void  getcontrasenna(){

return this.contrasenna;
}

public String setcontrasenna(String contrasenna){

this.contrasenna = contrasenna;
} 

public String  getgrado(){
return this.grado;
}


public void setgrado(String grado){
this.grado = grado;
} 

public String  getdetalles(){
return this.detalles;


}
public void setdetalles(String detalles){
this.detalles = detalles;
}

}
