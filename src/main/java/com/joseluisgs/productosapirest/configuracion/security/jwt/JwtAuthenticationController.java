package com.joseluisgs.productosapirest.configuracion.security.jwt;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import com.joseluisgs.productosapirest.configuracion.security.jwt.model.JwtUserResponse;
import com.joseluisgs.productosapirest.configuracion.security.jwt.model.LoginRequest;
import com.joseluisgs.productosapirest.usuarios.dto.converter.UsuarioDTOConverter;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import com.joseluisgs.productosapirest.usuarios.modelos.UsuarioRol;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIConfig.API_PATH + "/usuarios")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioDTOConverter converter;

    // Metodo post para el login
    @PostMapping(APIConfig.API_PATH + "/login")
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

    /* Me lo llevo a UsuarioController, porque me gusta más alli
    // Petición me de datos del isiatio
    @PreAuthorize("isAuthenticated()") // Equivalente en ponerlo en config, solo puede entrar si estamos auteticados
    @GetMapping(APIConfig.API_PATH + "/me")
    public GetUsuarioDTO me(@AuthenticationPrincipal Usuario user) {
        return converter.convertUserEntityToGetUserDto(user);
    }
    */

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

