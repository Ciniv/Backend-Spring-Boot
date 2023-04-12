package com.example.api.dto;

import lombok.Data;

@Data
public class UsuarioAutenticado {
    private String login;
    private String nome;
    private String token;
    private boolean administrador;
    private boolean autenticado;
}
