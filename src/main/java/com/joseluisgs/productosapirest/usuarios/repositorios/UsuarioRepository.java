package com.joseluisgs.productosapirest.usuarios.repositorios;

import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
