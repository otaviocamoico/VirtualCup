package interdisciplinar.com.interdisciplinar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
import interdisciplinar.com.interdisciplinar.enums.Role;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    boolean existsByEmailUsuario(String emailUsuario);

    long countByRole(Role role);
}
