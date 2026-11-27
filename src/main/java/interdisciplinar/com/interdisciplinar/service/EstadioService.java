
package interdisciplinar.com.interdisciplinar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import interdisciplinar.com.interdisciplinar.entity.Estadio;
import interdisciplinar.com.interdisciplinar.repository.EstadioRepository;

@Service
public class EstadioService {

    private final EstadioRepository estadioRepository;

    public EstadioService(EstadioRepository estadioRepository) {
        this.estadioRepository = estadioRepository;
    }

    // Cadastrar ou atualizar estádio
    public Estadio save(Estadio estadio) {
        return estadioRepository.save(estadio);
    }

    // Listar todos os estádios
    public List<Estadio> findAll() {
        return estadioRepository.findAll();
    }

    // Buscar estádio pelo ID
    public Optional<Estadio> findById(Integer id) {
        return estadioRepository.findById(id);
    }

    // Verificar se o estádio existe
    public boolean existsById(Integer id) {
        return estadioRepository.existsById(id);
    }

}
