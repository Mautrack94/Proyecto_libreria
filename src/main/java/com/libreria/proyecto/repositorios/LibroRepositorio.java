
package com.libreria.proyecto.repositorios;

import com.libreria.proyecto.entidades.Autor;
import com.libreria.proyecto.entidades.Editorial;
import com.libreria.proyecto.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    @Query("SELECT a FROM Libro a WHERE a.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT a FROM Libro a WHERE a.autor.nombre = :nombre")
    public List<Libro> buscarPorAutor(@Param("nombre") String nombre);
    
    @Query("SELECT a FROM Libro a WHERE a.editorial.nombre = :nombre")
    public List<Libro> buscarPorEditorial(@Param("nombre") String nombre);
}
