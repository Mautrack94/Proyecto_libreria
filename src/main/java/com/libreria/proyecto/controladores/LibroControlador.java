
package com.libreria.proyecto.controladores;

import com.libreria.proyecto.entidades.Autor;
import com.libreria.proyecto.entidades.Editorial;
import com.libreria.proyecto.entidades.Libro;
import com.libreria.proyecto.excepciones.ErrorServicio;
import com.libreria.proyecto.repositorios.AutorRepositorio;
import com.libreria.proyecto.servicios.AutorServicio;
import com.libreria.proyecto.servicios.EditorialServicio;
import com.libreria.proyecto.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/cargar")
    public String formularios(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        
        return "libroFormulario";
    }
    
    @PostMapping("/cargar")
    public String guardarLibro(ModelMap modelo, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial, Boolean alta) throws ErrorServicio{
        try{
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial, alta);
            modelo.put("exito", "carga del libro exitosa");
            return "redirect:/libro/cargar";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("prestados", ejemplaresPrestados);
            modelo.put("disponibles", ejemplaresRestantes);
            modelo.put("autor", autor);
            modelo.put("editorial", editorial);
            return "libroFormulario";
            
        }
    }
    @GetMapping("/modificar/{id}")
    public String modificarLibro(ModelMap modelo,@PathVariable String id){
        modelo.put("libro", libroServicio.mostrarPorId(id));
        return "libroFormularioModif";
    }
    @PostMapping("/modificar/{id}")
    public String modificarLibro(ModelMap modelo, @PathVariable String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial, Boolean alta)throws ErrorServicio{
        try{
            libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial, alta);
            modelo.put("Exito", "Modificación exitosa");
            return "redirect:/libro/lista";
        }catch(Exception e){
            modelo.put("Error", "No se pudo realizar el cambio");
            return "libroFormularioModif";
        }
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libros";
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws ErrorServicio{
        try{
            libroServicio.bajaLibro(id);
            return "redirect:/libro/lista";
        }catch(Exception e){
            return "redirect:/";  
        }
    }
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErrorServicio{
        try{
            libroServicio.habilitarLibro(id);
            return "redirect:/libro/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
}
