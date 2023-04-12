package com.example.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;

import com.example.api.dto.Login;
import com.example.api.dto.UsuarioAutenticado;
import com.example.api.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@Tag(name = "usuario")
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    
    @PostMapping("/autenticar")
    public UsuarioAutenticado login(@RequestBody Login login) {
        return userService.login(login.getLogin(), login.getPassword());
    }

    @GetMapping("/renovar-ticket")
    public UsuarioAutenticado renovar() {
        return userService.new_token(this.request);
    }

}
