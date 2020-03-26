package com.joseluisgs.productosapirest.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.joseluisgs.productosapirest.utilidades.vistas.ProductoView;
import lombok.*;


// Los DTO nos sirven entre otras cosas para filtrar información de una o varias clases, podría ser similar a las vistas
// Aplicamos lombok para hacer el DT0
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Solo tiene getter&setter
public class GetProductoDTO {

    // Indicamos los atributos que queremos que salga y como saldrán con JSON Views
    @JsonView(ProductoView.DTO.class)
    private long id;

    @JsonView(ProductoView.DTO.class)
    private String nombre;

    @JsonView(ProductoView.DTOConPrecio.class)
    private float precio;

    @JsonView(ProductoView.DTO.class)
    private String imagen;

    @JsonView(ProductoView.DTO.class)
    private String categoria;

}
