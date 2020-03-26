package com.joseluisgs.productosapirest.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPedidoDTO {

    private String fullName;
    private String email;
    private String avatar;
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha;
    private Set<GetLineaPedidoDTO> lineas;
    private float total;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetLineaPedidoDTO {


        private String productoNombre;
        private int cantidad;
        private float precioUnitario;
        private float subTotal;

    }



}
