package com.joseluisgs.productosapirest.usuarios.dto.converter;

import com.joseluisgs.productosapirest.usuarios.dto.GetUsuarioDTO;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import com.joseluisgs.productosapirest.usuarios.modelos.UsuarioRol;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UsuarioDTOConverter {

    public GetUsuarioDTO convertUserEntityToGetUserDTO(Usuario user) {
        return GetUsuarioDTO.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(user.getRoles().stream()
                        .map(UsuarioRol::name)
                        .collect(Collectors.toSet())
                ).build();
    }

}
