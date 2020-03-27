package com.joseluisgs.productosapirest.usuarios.controladores;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import com.joseluisgs.productosapirest.usuarios.dto.CreateUsuarioDTO;
import com.joseluisgs.productosapirest.usuarios.dto.GetUsuarioDTO;
import com.joseluisgs.productosapirest.usuarios.dto.converter.UsuarioDTOConverter;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import com.joseluisgs.productosapirest.usuarios.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController

// Esta va a ser la raiz de donde escuchemos es decir http://localhost/api/usuarios/
// Cuidado que se necesia la barra al final porque la estamos poniendo en los verbos
@RequestMapping(APIConfig.API_PATH+"/usuarios") // Sigue escucnado en el directorio API

@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService userEntityService;
    private final UsuarioDTOConverter userDtoConverter;


    @PostMapping("/")
    public GetUsuarioDTO nuevoUsuario(@RequestBody CreateUsuarioDTO newUser) {
        return userDtoConverter.convertUserEntityToGetUserDto(userEntityService.nuevoUsuario(newUser));

    }

    // Información del usuario
    @GetMapping("/me")
    public GetUsuarioDTO yo(@AuthenticationPrincipal Usuario user) {
        return userDtoConverter.convertUserEntityToGetUserDto(user);
    }

}
