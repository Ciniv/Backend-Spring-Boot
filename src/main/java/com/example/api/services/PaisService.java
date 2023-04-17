package com.example.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entities.Pais;
import com.example.api.repositories.PaisRepository;

@Service
public class PaisService {
    
    @Autowired
    private PaisRepository paisRepository;

    public List<Pais> get_pais() {
        return paisRepository.findAll();
    }

    public List<Pais> get_pais_text(String text){
        return paisRepository.findByText(text);
    }

    public Pais salvar(Pais pais){
        return paisRepository.save(pais);
    }

    public boolean deletar(Long id){
        Optional<Pais> pais = paisRepository.findById(id);
        if(pais.isPresent()){
            paisRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
