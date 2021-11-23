
package com.libreria.proyecto.servicios;

import com.libreria.proyecto.entidades.Autor;
import com.libreria.proyecto.entidades.Editorial;
import com.libreria.proyecto.excepciones.ErrorServicio;
import com.libreria.proyecto.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws ErrorServicio{
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        
        
        autorRepositorio.save(autor);
    }
    
    private void validar(String nombre) throws ErrorServicio{
        if(nombre.isEmpty() || nombre == null){
            throw new ErrorServicio("Debe ingresar el nombre del autor");
        }
       
        
    }
    
    @Transactional
    public void ModificarAutor(String id, String nombre) throws ErrorServicio{
        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
        Autor autor = respuesta.get();
        autor.setNombre(nombre);
        autor.setAlta(true);
        
        
        autorRepositorio.save(autor);
        
        }else{
            throw new ErrorServicio("No se encontró el autor solicitado");
        } 
    }
    
    @Transactional
    public void bajaAutor(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(false);
        }else{
            throw new ErrorServicio("No se encontró el autor solicitado");
        }
    }
    
    @Transactional
    public void habilitarAutor(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(true);
        }else{
            throw new ErrorServicio("No se encontró el autor solicitado");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAutores(){
        return autorRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Autor mostrarPorId(String id){
        return autorRepositorio.getById(id);
    }
}
