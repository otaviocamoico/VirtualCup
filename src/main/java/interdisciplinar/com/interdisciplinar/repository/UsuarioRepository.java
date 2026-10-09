package interdisciplinar.com.interdisciplinar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import interdisciplinar.com.interdisciplinar.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}