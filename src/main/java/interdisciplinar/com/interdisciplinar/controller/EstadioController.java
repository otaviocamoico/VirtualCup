package interdisciplinar.com.interdisciplinar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import interdisciplinar.com.interdisciplinar.entity.Estadio;
import interdisciplinar.com.interdisciplinar.service.EstadioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/estadios")
public class EstadioController {
    @Autowired
    private EstadioService estadioService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Estadio estadio) {
        estadioService.save(estadio);
        return "redirect:/usuarios/listar";
    }

     @GetMapping("/listar")
    public String listar(Model model) {
        List<Estadio> estadios = estadioService.findAll();
        model.addAttribute("estadios", estadios);
        return "estadio/ListarEstadios";
    }

     @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("estadio", new Estadio());
        return "estadio/formularioEstudio";
    }
    
}