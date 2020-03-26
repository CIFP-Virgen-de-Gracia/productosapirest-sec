package com.joseluisgs.productosapirest.usuarios.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Indicamos que es uns ervicio de detalles de usuario
@Service("userDetailsService") // Es muy importante esta línea para decir que vamos a usar el servicio de usuarios Spring
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService userEntityService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityService.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username + " no encontrado"));
    }

}