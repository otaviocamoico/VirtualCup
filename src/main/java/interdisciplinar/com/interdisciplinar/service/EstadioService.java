package interdisciplinar.com.interdisciplinar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interdisciplinar.com.interdisciplinar.entity.Estadio;
import interdisciplinar.com.interdisciplinar.repository.EstadioRepository;

@Service
public class EstadioService {
  @Autowired
  private EstadioRepository estadioRepository;  


public Estadio save(Estadio estadio){
    return estadioRepository.save(estadio);
    }

    public List<Estadio> findAll(){
        return estadioRepository.findAll();
    }

}
