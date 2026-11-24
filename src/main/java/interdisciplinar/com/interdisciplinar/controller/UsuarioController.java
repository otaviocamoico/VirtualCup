package interdisciplinar.com.interdisciplinar.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
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

            model.addAttribute(
                "erroCadastro",
                "Informe seu nome."
            );

            return "usuario/formularioUsuario";
        }

        usuario.setNomeUsuario(usuario.getNomeUsuario().trim());

        if (usuario.getNomeUsuario().length() > 40) {

            model.addAttribute(
                "erroCadastro",
                "O nome deve possuir no máximo 40 caracteres."
            );

            return "usuario/formularioUsuario";
        }

        // Validar e-mail
        if (usuario.getEmailUsuario() == null ||
                usuario.getEmailUsuario().isBlank()) {

            model.addAttribute(
                "erroCadastro",
                "Informe seu e-mail."
            );

            return "usuario/formularioUsuario";
        }

        usuario.setEmailUsuario(
            usuario.getEmailUsuario().trim().toLowerCase(Locale.ROOT)
        );

        if (usuario.getEmailUsuario().length() > 254) {

            model.addAttribute(
                "erroCadastro",
                "O e-mail deve possuir no máximo 254 caracteres."
            );

            return "usuario/formularioUsuario";
        }

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
        if (!usuario.getSenhaUsuario().equals(
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

            model.addAttribute(
                "erroCadastro",
                e.getMessage()
            );

            return "usuario/formularioUsuario";

        } catch (DataIntegrityViolationException e) {

            model.addAttribute(
                "erroCadastro",
                "Não foi possível concluir o cadastro. "
                + "Verifique se o e-mail já está cadastrado."
            );

            return "usuario/formularioUsuario";
        }
    }

    // Listar usuários
    @GetMapping("/listar")
    public String listar(Model model) {

        List<Usuario> usuarios = usuarioService.findAll();

        model.addAttribute("usuarios", usuarios);

        return "usuario/ListarUsuarios";
    }

    // Exibir formulário de cadastro
    @GetMapping("/criar")
    public String criarForm(Model model) {

        model.addAttribute("usuario", new Usuario());

        return "usuario/formularioUsuario";
    }
}
