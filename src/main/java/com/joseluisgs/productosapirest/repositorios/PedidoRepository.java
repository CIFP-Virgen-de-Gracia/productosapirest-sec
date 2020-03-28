package com.joseluisgs.productosapirest.repositorios;

import com.joseluisgs.productosapirest.modelos.Pedido;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByCliente(Usuario cliente, Pageable pageable);

}
