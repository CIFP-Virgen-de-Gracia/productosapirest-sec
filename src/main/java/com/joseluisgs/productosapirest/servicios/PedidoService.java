package com.joseluisgs.productosapirest.servicios;

import com.joseluisgs.productosapirest.dto.CreatePedidoDTO;
import com.joseluisgs.productosapirest.modelos.LineaPedido;
import com.joseluisgs.productosapirest.modelos.Pedido;
import com.joseluisgs.productosapirest.modelos.Producto;
import com.joseluisgs.productosapirest.repositorios.PedidoRepository;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PedidoService extends BaseService<Pedido, Long, PedidoRepository> {

    private final ProductoService productoService;

    public Pedido nuevoPedido(CreatePedidoDTO nuevo, Usuario cliente) {

        return save(Pedido.builder()
                .cliente(cliente)
                .lineas(nuevo.getLineas().stream()
                        .map(lineaDto -> {

                            Optional<Producto> p = productoService.findById(lineaDto.getProductoId());
                            if (p.isPresent()) {
                                Producto producto = p.get();
                                return LineaPedido.builder()
                                        .cantidad(lineaDto.getCantidad())
                                        .precio(producto.getPrecio())
                                        .producto(producto)
                                        .build();
                            } else {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())
                )
                .build());


    }

    // Todos los pedidos de un cliente
    public Page<Pedido> findAllByUser(Usuario user, Pageable pageable) {
        return repositorio.findByCliente(user, pageable);
    }
}
