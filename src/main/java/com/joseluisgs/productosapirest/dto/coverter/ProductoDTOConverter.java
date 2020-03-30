package com.joseluisgs.productosapirest.dto.coverter;

import com.joseluisgs.productosapirest.dto.CreateProductoDTO;
import com.joseluisgs.productosapirest.dto.GetProductoDTO;
import com.joseluisgs.productosapirest.modelos.Producto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor // Nos ahorramos el autowire
public class ProductoDTOConverter {

    private final ModelMapper modelMapper;


    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<Producto, GetProductoDTO>() {

            @Override
            protected void configure() {
                map().setCategoria(source.getCategoria().getNombre());
            }
        });
    }

    // Recibe un producto y lo trasforma en productoDTO

    /**
     * Opción 1 con ModelMapper
     *
     * @param producto
     * @return
     */
    public GetProductoDTO convertToDTO(Producto producto) {
        return modelMapper.map(producto, GetProductoDTO.class);

    }

    // Para convertir un prodtctoDTO en producto
    public Producto convertToProducto(CreateProductoDTO createProductoDTO) {
        return modelMapper.map(createProductoDTO, Producto.class);
    }

    /**
     * Opción 2 con Builder de Lombok
     *
     * @param producto
     * @return
     */
    public GetProductoDTO convertProdutoToProductoDTO(Producto producto) {
        return GetProductoDTO.builder()
                .nombre(producto.getNombre())
                .imagen(producto.getImagen())
                .precio(producto.getPrecio()) // Si no queremos algo lo quitamos de aqui
                .categoria(producto.getCategoria().getNombre())
                .id(producto.getId())
                .build();
    }


}
