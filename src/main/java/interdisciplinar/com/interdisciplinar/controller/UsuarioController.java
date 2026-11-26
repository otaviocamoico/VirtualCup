package interdisciplinar.com.interdisciplinar.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
import interdisciplinar.com.interdisciplinar.enums.Role;
import interdisciplinar.com.interdisciplinar.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Salvar usuário
    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute("usuario") Usuario usuario,
            Model model) {

        // Validar nome
        if (usuario.getNomeUsuario() == null ||
                usuario.getNomeUsuario().isBlank()) {

            model.addAttribute("erroCadastro", "Informe seu nome.");

            return "usuario/formularioUsuario";
        }

        usuario.setNomeUsuario(usuario.getNomeUsuario().trim());

        // Validar e-mail
        if (usuario.getEmailUsuario() == null ||
                usuario.getEmailUsuario().isBlank()) {

            model.addAttribute("erroCadastro", "Informe seu e-mail.");

            return "usuario/formularioUsuario";
        }

        usuario.setEmailUsuario(
            usuario.getEmailUsuario().trim()
        );

        // Validar senha
        if (usuario.getSenhaUsuario() == null ||
                usuario.getSenhaUsuario().length() < 8) {

            model.addAttribute(
                "erroCadastro",
                "A senha deve possuir pelo menos 8 caracteres."
            );

            return "usuario/formularioUsuario";
        }

        // Confirmar senha
        if (usuario.getConfirmarSenha() == null ||
                !usuario.getSenhaUsuario().equals(
                    usuario.getConfirmarSenha())) {

            model.addAttribute(
                "erroCadastro",
                "As senhas informadas não coincidem."
            );

            return "usuario/formularioUsuario";
        }

        try {

            usuarioService.save(usuario);

            return "redirect:/login?cadastro";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erroCadastro", e.getMessage());

            return "usuario/formularioUsuario";

        } catch (DataIntegrityViolationException e) {

            model.addAttribute(
                "erroCadastro",
                "Este e-mail já está cadastrado."
            );

            return "usuario/formularioUsuario";
        }
    }

    // Listar usuários
    @GetMapping("/listar")
    public String listar(
            Model model,
            Authentication authentication) {

        List<Usuario> usuarios = usuarioService.findAll();

        model.addAttribute("usuarios", usuarios);

        model.addAttribute(
            "emailAdministradorAtual",
            authentication.getName()
        );

        return "usuario/listarUsuarios";
    }

    // Promover usuário para administrador
    @PostMapping("/{id}/promover")
    public String promover(
            @PathVariable Integer id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {

            usuarioService.alterarPerfil(
                id,
                Role.ADMIN,
                authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                "mensagem",
                "Usuário promovido para administrador com sucesso."
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                "erro",
                e.getMessage()
            );
        }

        return "redirect:/usuarios/listar";
    }

    // Rebaixar administrador para usuário comum
    @PostMapping("/{id}/rebaixar")
    public String rebaixar(
            @PathVariable Integer id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {

            usuarioService.alterarPerfil(
                id,
                Role.USER,
                authentication.getName()
            );

            redirectAttributes.addFlashAttribute(
                "mensagem",
                "Administrador rebaixado para usuário comum com sucesso."
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                "erro",
                e.getMessage()
            );
        }

        return "redirect:/usuarios/listar";
    }

    // Exibir formulário de cadastro
    @GetMapping("/criar")
    public String criarForm(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "usuario/formularioUsuario";
    }
}
