package interdisciplinar.com.interdisciplinar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import interdisciplinar.com.interdisciplinar.entity.Selecao;
import interdisciplinar.com.interdisciplinar.repository.SelecaoRepository;

@Service
public class SelecaoService {
  @Autowired
  private SelecaoRepository selecaoRepository;  


public Selecao save(Selecao selecao){
    return selecaoRepository.save(selecao);
    }

    public List<Selecao> findAll(){
        return selecaoRepository.findAll();
    }

}
