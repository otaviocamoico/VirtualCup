package interdisciplinar.com.interdisciplinar.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import interdisciplinar.com.interdisciplinar.repository.UsuarioRepository;

@Controller
@RequestMapping("/virtualcup")
public class VirtualCupController {

    private final UsuarioRepository usuarioRepository;

    public VirtualCupController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Página inicial
    @GetMapping
    public String index() {
        return "index";
    }

    // Página inicial do usuário comum
    @GetMapping("/cliente/home")
    public String homeCliente(Authentication authentication, Model model) {

        usuarioRepository.findByEmailUsuario(authentication.getName())
                .ifPresent(usuario ->
                        model.addAttribute("nomeUsuario", usuario.getNomeUsuario())
                );

        return "cliente/homeCliente";
    }

    // Página inicial do administrador
    @GetMapping("/admin/home")
    public String homeAdmin() {
        return "administrador/homeAdmin";
    }
}
