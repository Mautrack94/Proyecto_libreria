
package com.libreria.proyecto.controladores;

import com.libreria.proyecto.entidades.Autor;
import com.libreria.proyecto.excepciones.ErrorServicio;
import com.libreria.proyecto.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/cargar")
    public String formulario(){
        return "autorFormulario";
    }
    @PostMapping("/cargar")
    public String guardarAutor(ModelMap modelo, String nombre)throws ErrorServicio{
        try{
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "Carga del autor exitosa");
            return "autorFormulario";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            return "autorFormulario";
        }
    }
    @GetMapping("/modificar/{id}")
    public String modificarAutor(ModelMap modelo, @PathVariable String id) throws ErrorServicio{
       modelo.put("autor", autorServicio.mostrarPorId(id));
       return "autorFormularioModif";
    }
    @PostMapping("/modificar/{id}")
    public String modificarAutor(ModelMap modelo, @PathVariable String id, String nombre) throws ErrorServicio{
         try{
            autorServicio.ModificarAutor(id, nombre);
            modelo.put("exito", "Modificación exitosa");
            return "redirect:/autor/lista";
        }catch(Exception e){
            modelo.put("error", "No se pudo realizar el cambio");
             System.out.println(e.getMessage());
            return "autorFormularioModif";
        }
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autores";
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) throws ErrorServicio{
        try{
            autorServicio.bajaAutor(id);
            return "redirect:/autor/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErrorServicio{
        try{
            autorServicio.habilitarAutor(id);
            return "redirect:/autor/lista";
        }catch(Exception e){
            return "redirect:/";
        }
    }
    
}
