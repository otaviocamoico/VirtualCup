package interdisciplinar.com.interdisciplinar.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import interdisciplinar.com.interdisciplinar.entity.Partida;
import interdisciplinar.com.interdisciplinar.repository.PartidaRepository;
 
@Service
public class PartidaService {
  @Autowired
  private PartidaRepository partidaRepository;  
 
 
public Partida save(Partida partida){
    return partidaRepository.save(partida);
    }
 
    public List<Partida> findAll(){
        return partidaRepository.findAll();
    }
 
}