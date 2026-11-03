package interdisciplinar.com.interdisciplinar.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import interdisciplinar.com.interdisciplinar.entity.Ingresso;
import interdisciplinar.com.interdisciplinar.service.IngressoService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
 
 
@Controller
@RequestMapping("/ingressos")
public class IngressoController {
    @Autowired
    private IngressoService ingressoService;
 
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Ingresso ingresso) {
        ingressoService.save(ingresso);
        return "redirect:/ingressos/listar";
    }
 
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Ingresso> ingressos = ingressoService.findAll();
        model.addAttribute("ingressos",ingressos);
        return "ingresso/ListarIngressos";
    }
   
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("ingresso", new Ingresso());
        return "ingresso/formularioIngresso";
    }
   
   
}
 