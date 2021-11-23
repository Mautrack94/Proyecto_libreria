
package com.libreria.proyecto.servicios;

import com.libreria.proyecto.entidades.Editorial;
import com.libreria.proyecto.excepciones.ErrorServicio;
import com.libreria.proyecto.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws ErrorServicio{
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        editorialRepositorio.save(editorial);
    }
    
    private void validar(String nombre) throws ErrorServicio{
        if(nombre.isEmpty() || nombre == null){
            throw new ErrorServicio("Debe indicar el nombre de la editorial");
        }
        
    }
    
    @Transactional
    public void modificarEditorial(String id, String nombre) throws ErrorServicio{
        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorial.setAlta(true);
            
            editorialRepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada");
        }
    }
    
    @Transactional
    public void bajaEditorial(String id) throws ErrorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada");
        }
    }
    
    @Transactional
    public void habilitarEditorial(String id) throws ErrorServicio{
         Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Editorial mostrarPorId(String id){
        return editorialRepositorio.getById(id);
    }
}
