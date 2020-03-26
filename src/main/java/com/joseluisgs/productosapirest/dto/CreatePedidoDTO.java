package com.joseluisgs.productosapirest.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePedidoDTO {

    private List<CreateLineaPedidoDTO> lineas;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CreateLineaPedidoDTO {

        private int cantidad;
        private Long productoId;

    }



}
