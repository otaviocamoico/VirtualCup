package interdisciplinar.com.interdisciplinar.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
 
import interdisciplinar.com.interdisciplinar.entity.Compra;
import interdisciplinar.com.interdisciplinar.service.CompraService;
 
@Controller
@RequestMapping("/compras")
public class CompraController {
 
    @Autowired
    private CompraService compraService;
 
    // Método para salvar uma compra
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Compra compra) {
        compraService.save(compra);
        return "redirect:/compras/listar";
    }
 
    // Método para listar todas as compras
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Compra> compras = compraService.findAll();
        model.addAttribute("compras", compras);
        return "compra/formularioCompra";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("compra", new Compra());
        return "compra/formularioCompra";
}

}