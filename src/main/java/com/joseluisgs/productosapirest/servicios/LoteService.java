package com.joseluisgs.productosapirest.servicios;

import com.joseluisgs.productosapirest.dto.CreateLoteDTO;
import com.joseluisgs.productosapirest.errores.excepciones.LoteCreateException;
import com.joseluisgs.productosapirest.modelos.Lote;
import com.joseluisgs.productosapirest.repositorios.LoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoteService extends BaseService<Lote, Long, LoteRepository> {

    private final ProductoService productoService;

    @Override
    public Optional<Lote> findById(Long id) {
        return repositorio.findByIdJoinFetch(id);
    }

    public Lote nuevoLote(CreateLoteDTO nuevoLote) {

        Lote l = Lote.builder()
                .nombre(nuevoLote.getNombre())
                .build();

        nuevoLote.getProductos().stream()
                .map(id -> {
                    return productoService.findByIdConLotes(id).orElseThrow(() -> new LoteCreateException());
                })
                .forEach(l::addProducto);

        return save(l);

    }



}
