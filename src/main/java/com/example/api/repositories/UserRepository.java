package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Usuario;

public interface UserRepository extends JpaRepository<Usuario,Long>{

    Usuario findByLogin(String login);
    
}
