package interdisciplinar.com.interdisciplinar.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import interdisciplinar.com.interdisciplinar.entity.Partida;
import interdisciplinar.com.interdisciplinar.service.PartidaService;
 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
 
 
@Controller
@RequestMapping("/partidas")
public class PartidaController {
    @Autowired
    private PartidaService partidaService;
 
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Partida partida) {
        partidaService.save(partida);
        return "redirect:/partidas/listar";
    }
 
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Partida> partidas = partidaService.findAll();
        model.addAttribute("partidas", partidas);
        return "partida/ListarPartidas";
    }
   
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("partida", new Partida());
        return "partida/formularioPartida";
    }
   
   
}