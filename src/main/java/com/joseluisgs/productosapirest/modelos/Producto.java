package com.joseluisgs.productosapirest.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Producto.class )
public class Producto {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private float precio;

    private String imagen;


    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    //@JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
//	@ManyToMany(mappedBy="productos", fetch = FetchType.EAGER)
    @ManyToMany(mappedBy="productos")
    @Builder.Default
    private Set<Lote> lotes = new HashSet<>();


}
