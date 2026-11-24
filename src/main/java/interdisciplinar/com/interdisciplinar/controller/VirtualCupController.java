package interdisciplinar.com.interdisciplinar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/virtualcup")
public class VirtualCupController {

    // Página inicial
    @GetMapping
    public String index() {
        return "index";
    }

    // Página inicial do usuário comum
    @GetMapping("/cliente/home")
    public String homeCliente() {
        return "cliente/homeCliente";
    }

    // Página inicial do administrador
    @GetMapping("/admin/home")
    public String homeAdmin() {
        return "administrador/homeAdmin";
    }
}
