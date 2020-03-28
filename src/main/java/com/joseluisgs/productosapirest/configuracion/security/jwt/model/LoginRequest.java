package com.joseluisgs.productosapirest.configuracion.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor

// Para el login
public class LoginRequest {

    @NotBlank // No envíen datos en blanco
    private String username;
    @NotBlank
    private String password;

}
