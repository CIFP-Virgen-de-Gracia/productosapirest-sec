package com.joseluisgs.productosapirest.repositorios;

import com.joseluisgs.productosapirest.modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
