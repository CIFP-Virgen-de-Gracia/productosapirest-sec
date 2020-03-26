package com.joseluisgs.productosapirest.modelos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


// Muchos a muchos

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Lote.class)
public class Lote {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    //@JsonManagedReference
    //@ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="lote_id"),
            inverseJoinColumns = @JoinColumn(name="producto_id")
    )
    @Builder.Default
    private Set<Producto> productos = new HashSet<>();

    /**
     * Métodos helper
     */
    public void addProducto(Producto p) {
        this.productos.add(p);
        p.getLotes().add(this);
    }

    public void deleteProducto(Producto p) {
        this.productos.remove(p);
        p.getLotes().remove(this);
    }

}
