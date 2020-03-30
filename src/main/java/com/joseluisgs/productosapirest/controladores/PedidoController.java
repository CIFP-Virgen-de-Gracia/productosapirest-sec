package com.joseluisgs.productosapirest.controladores;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import com.joseluisgs.productosapirest.dto.CreatePedidoDTO;
import com.joseluisgs.productosapirest.dto.GetPedidoDTO;
import com.joseluisgs.productosapirest.dto.coverter.PedidoDTOConverter;
import com.joseluisgs.productosapirest.errores.ApiError;
import com.joseluisgs.productosapirest.errores.excepciones.PedidoNotFoundException;
import com.joseluisgs.productosapirest.modelos.Pedido;
import com.joseluisgs.productosapirest.servicios.PedidoService;
import com.joseluisgs.productosapirest.usuarios.modelos.Usuario;
import com.joseluisgs.productosapirest.usuarios.modelos.UsuarioRol;
import com.joseluisgs.productosapirest.utilidades.paginacion.PaginationLinksUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController

// Esta va a ser la raiz de donde escuchemos es decir http://localhost/api/pedidos/
// Cuidado que se necesia la barra al final porque la estamos poniendo en los verbos
@RequestMapping(APIConfig.API_PATH + "/pedidos") // Sigue escucnado en el directorio API
@RequiredArgsConstructor // Lombok
public class PedidoController {

    // Nos ahorramos autowored por lombok poniendo final
    private final PedidoService pedidoService;
    private final PaginationLinksUtils paginationLinksUtils;
    private final PedidoDTOConverter pedidoDtoConverter;


    // Método GET
    @ApiOperation(value = "Obtiene una lista de pedidos", notes = "Obtiene una lista de pedidos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Pedido.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> pedidos(Pageable pageable, HttpServletRequest request, @AuthenticationPrincipal Usuario user) throws PedidoNotFoundException {
        //Buscamos
        //Page<Pedido> result = pedidoService.findAll(pageable);

        Page<Pedido> result = null;
        // Si es administrador, puede ver todos los pedidos; si no, solo verá los
        // propios
        if (user.getRoles().contains(UsuarioRol.ADMIN)) {
            result = pedidoService.findAll(pageable);
        } else {
            result = pedidoService.findAllByUser(user, pageable);
        }

        // Si no es vacío
        if (result.isEmpty()) {
            throw new PedidoNotFoundException();
        } else {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

            return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(result, uriBuilder))
                    .body(result);


        }
    }

    @PostMapping("/")
    public ResponseEntity<GetPedidoDTO> nuevoPedido(@RequestBody CreatePedidoDTO pedido, @AuthenticationPrincipal Usuario user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoDtoConverter.convertPedidoToGetPedidoDTO(pedidoService.nuevoPedido(pedido, user)));
    }



}
