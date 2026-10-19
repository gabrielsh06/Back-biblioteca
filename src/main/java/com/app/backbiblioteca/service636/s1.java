/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.backbiblioteca.service636;

import com.app.backbiblioteca.user.domain.User;
import com.app.backbiblioteca.user.repository.UserRepository;
import java.util.List;

/**
 *
 * @author krr
 */

/*
m12 ---> interface del repositorio 
us ----> usuario
------------------
d1b: 1 delete  2 save  3 update 
d2b: read all  
d3b: read one




*/


public class s1 {
    private UserRepository m12 ;
    public s1(UserRepository m12){this.m12 = m12;}
    public void d1b(int i, User us){
    switch(i){
        case 1:
            m12.deleteById(us.getId());
            break;
        case 2:
            m12.save(us);
            break;
        case 3:
            User usname = m12.findById(us.getId()).get();
            usname.setFirstName(us.getFirstName());
            usname.setLastName(us.getLastName());
            usname.setRoll(us.getRoll(););
            usname.setEmail(us.getEmail(););
            usname.setPassword(us.getPassWord(););
            m12.save(usname);
            
            break;
            
            
       
            
            default:
                System.out.println("No hay reguistro de accion 1d 2c 3u ");
    
    }
    
    
    
    
    
    }
    
    
    
    public List<User> d2b(){
    return m12.findAll();
    }
    
    
    public User d3b(long i){
    return m12.findById(i).get();
    }
    
    
}
