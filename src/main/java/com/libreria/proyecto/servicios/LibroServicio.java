
package com.libreria.proyecto.servicios;

import com.libreria.proyecto.entidades.Autor;
import com.libreria.proyecto.entidades.Editorial;
import com.libreria.proyecto.entidades.Libro;
import com.libreria.proyecto.excepciones.ErrorServicio;
import com.libreria.proyecto.repositorios.AutorRepositorio;
import com.libreria.proyecto.repositorios.EditorialRepositorio;
import com.libreria.proyecto.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial, Boolean alta) throws ErrorServicio{
        
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial, alta);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(true);
        libro.setAutor(autorRepositorio.save(autor));
        libro.setEditorial(editorialRepositorio.save(editorial));
        
        libroRepositorio.save(libro);
    }
    
    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial, Boolean alta) throws ErrorServicio{
        
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial, alta);
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
        Libro libro = respuesta.get();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(true);
        libro.setAutor(autorRepositorio.save(autor));
        libro.setEditorial(editorialRepositorio.save(editorial));
        
        libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontró el libro solicitado");
        }
    }
    
    private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial, Boolean alta) throws ErrorServicio{
       if(isbn <= 0 || isbn == null){
           throw new ErrorServicio("Debe ingresar un isbn válido");
       } 
       if(titulo.isEmpty() || titulo == null){
           throw new ErrorServicio("Debe ingresar el título del libro");
       }
       if(anio <= 0 || anio == null){
           throw new ErrorServicio("Debe ingresar el año de publicación");
       }
       if(ejemplares < 0 || ejemplares == null){
           throw new ErrorServicio("Debe ingresar la cantidad total de ejemplares");
       }
       if(ejemplaresPrestados < 0 || ejemplaresPrestados > ejemplares){
           throw new ErrorServicio("Debe ingresar la cantidad correcta de ejemplares prestados");
       }
       if(ejemplaresRestantes < 0 || ejemplaresRestantes > ejemplares){
           throw new ErrorServicio("Debe ingresar la cantidad correcta de ejemplares restantes");
       }
       if(autor == null){
           throw new ErrorServicio("Debe ingresar el autor del libro");
       }
       if(editorial == null){
           throw new ErrorServicio("Debe ingresar la editorial del libro");
       }
    }
    
    @Transactional
    public void bajaLibro(String id) throws ErrorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
        Libro libro = respuesta.get();
        libro.setAlta(false);
        }else{
            throw new ErrorServicio("No se encontró el libro solicitado");
        }
    }
    
    @Transactional
    public void habilitarLibro(String id) throws ErrorServicio{
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if(respuesta.isPresent()){
        Libro libro = respuesta.get();
        libro.setAlta(true);
        }else{
            throw new ErrorServicio("No se encontró el libro solicitado");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Libro> listarLibros(){
        return libroRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Libro mostarPorTitulo(String titulo){
       return libroRepositorio.buscarPorTitulo(titulo);
         
    }
    @Transactional
    public Editorial guardarEditorial(String nombre){
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        return editorialRepositorio.save(editorial);
    }
    
    @Transactional(readOnly = true)
    public Libro mostrarPorId(String id){
        return libroRepositorio.getById(id);
    }
}
