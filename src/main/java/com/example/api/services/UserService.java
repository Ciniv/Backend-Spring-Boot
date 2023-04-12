package com.example.api.services;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.api.dto.UsuarioAutenticado;
import com.example.api.repositories.UserRepository;
import com.example.api.security.TokenService;
import com.example.api.entities.Usuario;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    private Hashtable<String, UsuarioAutenticado> hashtable = new Hashtable<>();

    public UsuarioAutenticado login(String login, String password){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, password);

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var usuario = (Usuario) authenticate.getPrincipal();

        String token = this.tokenService.gerarToken(usuario);

        UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();

        usuarioAutenticado.setLogin(usuario.getLogin());
        usuarioAutenticado.setNome(usuario.getNome());
        usuarioAutenticado.setToken(token);
        if (usuario.getAdministrador().equals("ADMIN")){
            usuarioAutenticado.setAdministrador(true);
        } else {
            usuarioAutenticado.setAdministrador(false);
        }
        usuarioAutenticado.setAutenticado(authenticate.isAuthenticated());

        this.hashtable.put(usuario.getLogin(), usuarioAutenticado);

        return usuarioAutenticado;
    }
    

    public UsuarioAutenticado new_token(HttpServletRequest request) {
        String token;

        var authorizationHeader = request.getHeader("Authorization");

        try {
            if(authorizationHeader != null){
                token = authorizationHeader.replace("Bearer ", "");
                this.tokenService.getSubject(token);

                Usuario usuario = this.userRepository.findByLogin(JWT.decode(token).getSubject());

                String new_token = tokenService.gerarToken(usuario);
                UsuarioAutenticado user = this.hashtable.get(usuario.getLogin());
                user.setToken(new_token);

                return user;
            } else {
                return new UsuarioAutenticado();
            }
        } catch (TokenExpiredException e) {
            token = authorizationHeader.replace("Bearer ", "");

            Usuario usuario = this.userRepository.findByLogin(JWT.decode(token).getSubject());

            String new_token = tokenService.gerarToken(usuario);
            UsuarioAutenticado user = this.hashtable.get(usuario.getLogin());
            user.setToken(new_token);

            return user;
        } catch (Exception e) {
            return new UsuarioAutenticado();
        }
    }
}
