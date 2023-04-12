package com.example.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.entities.Pais;
import com.example.api.repositories.PaisRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pais")
@Tag(name = "pais")
@SecurityRequirement(name = "Bearer Authentication")
public class PaisController {

    @Autowired
    private PaisRepository paisRepository;
    
    @GetMapping()
    public List<Pais> listar(@RequestParam(required = false) String text) {
        if (text == null){
            return paisRepository.findAll();    
        }
        return paisRepository.findByText(text);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pais salvar(@RequestBody Pais pais) {
        try {
            return paisRepository.save(pais);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public boolean deletar(@PathVariable Long id) {
        Optional<Pais> pais = paisRepository.findById(id);
        if(pais.isPresent()){
            paisRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
