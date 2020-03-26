package com.joseluisgs.productosapirest.dto.coverter;

import com.joseluisgs.productosapirest.dto.GetPedidoDTO;
import com.joseluisgs.productosapirest.modelos.LineaPedido;
import com.joseluisgs.productosapirest.modelos.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoDTOConverter {

    public GetPedidoDTO convertPedidoToGetPedidoDTO(Pedido pedido) {

        return GetPedidoDTO.builder()
                .fullName(pedido.getCliente().getFullName())
                .avatar(pedido.getCliente().getAvatar())
                .email(pedido.getCliente().getEmail())
                .fecha(pedido.getFecha())
                .total(pedido.getTotal())
                .lineas(pedido.getLineas().stream()
                        .map(this::convertLineaPedidoToGetLineaPedidoDto)
                        .collect(Collectors.toSet())
                )
                .build();


    }

    public GetPedidoDTO.GetLineaPedidoDTO convertLineaPedidoToGetLineaPedidoDto(LineaPedido linea) {
        return GetPedidoDTO.GetLineaPedidoDTO.builder()
                .cantidad(linea.getCantidad())
                .precioUnitario(linea.getPrecio())
                .productoNombre(linea.getProducto().getNombre())
                .subTotal(linea.getSubtotal())
                .build();
    }

}
