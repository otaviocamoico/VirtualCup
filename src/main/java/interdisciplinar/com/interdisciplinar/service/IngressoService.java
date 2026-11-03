package interdisciplinar.com.interdisciplinar.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import interdisciplinar.com.interdisciplinar.entity.Ingresso;
import interdisciplinar.com.interdisciplinar.repository.IngressoRepository;
 
@Service
public class IngressoService {
  @Autowired
  private IngressoRepository ingressoRepository;  
 
 
public Ingresso save(Ingresso ingresso){
    return ingressoRepository.save(ingresso);
    }
 
    public List<Ingresso> findAll(){
        return ingressoRepository.findAll();
    }
 
}
 