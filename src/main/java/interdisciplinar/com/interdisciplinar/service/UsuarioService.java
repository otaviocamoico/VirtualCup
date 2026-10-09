package interdisciplinar.com.interdisciplinar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interdisciplinar.com.interdisciplinar.entity.Usuario;
import interdisciplinar.com.interdisciplinar.repository.UsuarioRepository;

@Service
public class UsuarioService {
  @Autowired
  private UsuarioRepository usuarioRepository;  


public Usuario save(Usuario usuario){
    return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

}
