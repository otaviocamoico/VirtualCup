
package interdisciplinar.com.interdisciplinar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import interdisciplinar.com.interdisciplinar.entity.Estadio;
import interdisciplinar.com.interdisciplinar.service.EstadioService;

@Controller
@RequestMapping("/estadios")
public class EstadioController {

    private final EstadioService estadioService;

    public EstadioController(EstadioService estadioService) {
        this.estadioService = estadioService;
    }

    // Cadastrar estádio
    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute("estadio") Estadio estadio,
            RedirectAttributes redirectAttributes) {

        estadioService.save(estadio);

        redirectAttributes.addFlashAttribute(
            "mensagem",
            "Estádio cadastrado com sucesso!"
        );

        return "redirect:/estadios/listar";
    }

    // Listar estádios
    @GetMapping("/listar")
    public String listar(Model model) {

        List<Estadio> estadios = estadioService.findAll();

        model.addAttribute("estadios", estadios);

        return "estadio/listarEstadios";
    }

    // Abrir formulário de cadastro
    @GetMapping("/criar")
    public String criarForm(Model model) {

        model.addAttribute("estadio", new Estadio());
        model.addAttribute("modoEdicao", false);

        return "estadio/formularioEstadio";
    }

    // Abrir formulário de edição
    @GetMapping("/{id}/editar")
    public String editarForm(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Estadio> resultado = estadioService.findById(id);

        if (resultado.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                "erro",
                "Estádio não encontrado."
            );

            return "redirect:/estadios/listar";
        }

        model.addAttribute("estadio", resultado.get());
        model.addAttribute("modoEdicao", true);

        return "estadio/formularioEstadio";
    }

    // Atualizar estádio
    @PostMapping("/{id}/atualizar")
    public String atualizar(
            @PathVariable("id") Integer id,
            @ModelAttribute("estadio") Estadio estadio,
            RedirectAttributes redirectAttributes) {

        if (!estadioService.existsById(id)) {

            redirectAttributes.addFlashAttribute(
                "erro",
                "Não foi possível atualizar: estádio não encontrado."
            );

            return "redirect:/estadios/listar";
        }

        // Preserva o ID do registro que será atualizado
        estadio.setIdEstadio(id);

        estadioService.save(estadio);

        redirectAttributes.addFlashAttribute(
            "mensagem",
            "Estádio atualizado com sucesso!"
        );

        return "redirect:/estadios/listar";
    }

}
