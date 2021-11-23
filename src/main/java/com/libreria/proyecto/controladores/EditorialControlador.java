
package com.libreria.proyecto.controladores;

import com.libreria.proyecto.entidades.Editorial;
import com.libreria.proyecto.excepciones.ErrorServicio;
import com.libreria.proyecto.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    EditorialServicio editorialServicio;
    
    @GetMapping("/cargar")
    public String formulario(){
        return "editorialFormulario";
    }
    
    @PostMapping("/cargar")
    public String formulario(ModelMap modelo, String nombre) throws ErrorServicio{
        try{
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Carga de la editorial exitosa");
            return "editorialFormulario";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            return "editorialFormulario";
                
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarEditorial(ModelMap modelo, @PathVariable String id) throws ErrorServicio{
        modelo.put("editorial", editorialServicio.mostrarPorId(id));
        return "editorialFormularioModif";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificarEditorial(ModelMap modelo, @PathVariable String id, String nombre) throws ErrorServicio{
        try{
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("exito", "Modificación de la editorial exitosa");
            return "redirect:/editorial/lista";
        }catch(Exception e){
            modelo.put("error", "No se pudo realizar la modificación");
            return "editorialFormularioModif";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editoriales";
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws ErrorServicio{
        try{
            editorialServicio.bajaEditorial(id);
            return "redirect:/editorial/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErrorServicio{
        try{
            editorialServicio.habilitarEditorial(id);
            return "redirect:/editorial/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
}
