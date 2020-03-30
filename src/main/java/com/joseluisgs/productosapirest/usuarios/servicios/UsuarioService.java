package com.joseluisgs.productosapirest.usuarios.servicios;

import com.joseluisgs.productosapirest.errores.excepciones.NewUserWithDifferentPasswordsException;
import com.joseluisgs.productosapirest.servicios.base.BaseService;
import com.joseluisgs.productosapirest.usuarios.dto.CreateUsuarioDTO;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import com.joseluisgs.productosapirest.usuarios.modelos.UsuarioRol;
import com.joseluisgs.productosapirest.usuarios.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, Long, UsuarioRepository> {

    private final PasswordEncoder passwordEncoder;

    /**
     * Nos permite buscar un usuario por su nombre de usuario
     *
     * @param username
     * @return
     */
    public Optional<Usuario> findUserByUsername(String username) {
        return this.repositorio.findByUsername(username);
    }

    /**
     * Nos permite crear un nuevo Usuario con rol USER
     *
     * @param newUser
     * @return
     */
    public Usuario nuevoUsuario(CreateUsuarioDTO newUser) {

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            Usuario usuario = Usuario.builder()
                    .username(newUser.getUsername())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .fullName(newUser.getFullname()).email(newUser.getEmail())
                    .roles(Stream.of(UsuarioRol.USER).collect(Collectors.toSet()))
                    .build();
            try {
                return save(usuario);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya existe");
            }
        } else {
            throw new NewUserWithDifferentPasswordsException();
        }

    }

}
