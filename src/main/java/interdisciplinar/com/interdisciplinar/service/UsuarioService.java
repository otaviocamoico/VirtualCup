package interdisciplinar.com.interdisciplinar.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
import interdisciplinar.com.interdisciplinar.enums.Role;
import interdisciplinar.com.interdisciplinar.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Salvar usuário
    public Usuario save(Usuario usuario) {

        // Validar nome
        if (usuario.getNomeUsuario() == null ||
                usuario.getNomeUsuario().isBlank()) {

            throw new IllegalArgumentException(
                "O nome do usuário é obrigatório."
            );
        }

        usuario.setNomeUsuario(usuario.getNomeUsuario().trim());

        // Validar e normalizar e-mail
        if (usuario.getEmailUsuario() == null ||
                usuario.getEmailUsuario().isBlank()) {

            throw new IllegalArgumentException(
                "O e-mail é obrigatório."
            );
        }

        String email = usuario.getEmailUsuario()
            .trim()
            .toLowerCase(Locale.ROOT);

        // Verificar tamanho do e-mail
        if (email.length() > 254) {

            throw new IllegalArgumentException(
                "O e-mail deve possuir no máximo 254 caracteres."
            );
        }

        // Verificar e-mail duplicado
        if (usuarioRepository.existsByEmailUsuario(email)) {

            throw new IllegalArgumentException(
                "Este e-mail já está cadastrado."
            );
        }

        usuario.setEmailUsuario(email);

        // Validar senha
        if (usuario.getSenhaUsuario() == null ||
                usuario.getSenhaUsuario().length() < 8) {

            throw new IllegalArgumentException(
                "A senha deve possuir pelo menos 8 caracteres."
            );
        }

        // Definir perfil inicial
        usuario.setRole(Role.USER);

        // Criptografar senha com BCrypt
        usuario.setSenhaUsuario(
            passwordEncoder.encode(usuario.getSenhaUsuario())
        );

        // Persistir no PostgreSQL
        return usuarioRepository.save(usuario);
    }

    // Listar usuários
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
