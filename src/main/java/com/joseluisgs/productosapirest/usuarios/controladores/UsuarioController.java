package com.joseluisgs.productosapirest.usuarios.controladores;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import com.joseluisgs.productosapirest.configuracion.security.jwt.JwtTokenProvider;
import com.joseluisgs.productosapirest.configuracion.security.jwt.model.JwtUserResponse;
import com.joseluisgs.productosapirest.configuracion.security.jwt.model.LoginRequest;
import com.joseluisgs.productosapirest.usuarios.dto.CreateUsuarioDTO;
import com.joseluisgs.productosapirest.usuarios.dto.GetUsuarioDTO;
import com.joseluisgs.productosapirest.usuarios.dto.converter.UsuarioDTOConverter;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import com.joseluisgs.productosapirest.usuarios.modelos.UsuarioRol;
import com.joseluisgs.productosapirest.usuarios.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController

// Esta va a ser la raiz de donde escuchemos es decir http://localhost/api/usuarios/
// Cuidado que se necesia la barra al final porque la estamos poniendo en los verbos
@RequestMapping(APIConfig.API_PATH + "/usuarios") // Sigue escucnado en el directorio API

@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService userEntityService;
    private final UsuarioDTOConverter userDtoConverter;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    @PostMapping("/")
    public GetUsuarioDTO nuevoUsuario(@RequestBody CreateUsuarioDTO newUser) {
        return userDtoConverter.convertUserEntityToGetUserDTO(userEntityService.nuevoUsuario(newUser));

    }

    // Petición me de datos del usuario
    @PreAuthorize("isAuthenticated()") // Equivalente en ponerlo en config, solo puede entrar si estamos auteticados
    @GetMapping("/me")
    public GetUsuarioDTO me(@AuthenticationPrincipal Usuario user) {
        return userDtoConverter.convertUserEntityToGetUserDTO(user);
    }

    // Metodo post para el login, todo traido de JwtAuthenticationController
    @PostMapping("/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()

                        )
                );

        // Autenticamos al usuario, si lo es nos lo devuelve
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Devolvemos al usuario autenticado
        Usuario user = (Usuario) authentication.getPrincipal();

        // Generamos el token
        String jwtToken = tokenProvider.generateToken(authentication);

        // La respuesta que queremos
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);

    }

    // Convertimos un usuario en un jwtUserResponseDTO
    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(Usuario user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .roles(user.getRoles().stream().map(UsuarioRol::name).collect(Collectors.toSet()))
                .token(jwtToken)
                .build();

    }


}
