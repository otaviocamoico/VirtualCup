package interdisciplinar.com.interdisciplinar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import interdisciplinar.com.interdisciplinar.entity.Selecao;
import interdisciplinar.com.interdisciplinar.service.SelecaoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/selecoes")
public class SelecaoController {
    @Autowired
    private SelecaoService selecaoService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Selecao selecao) {
        selecaoService.save(selecao);
        return "redirect:/selecoes/listar";
    }

     @GetMapping("/listar")
    public String listar(Model model) {
        List<Selecao> selecoes = selecaoService.findAll();
        model.addAttribute("selecoes", selecoes);
        return "selecao/ListarSelecoes";
    }

     @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("selecao", new Selecao());
        return "selecao/formularioSelecao";
    }
    
}