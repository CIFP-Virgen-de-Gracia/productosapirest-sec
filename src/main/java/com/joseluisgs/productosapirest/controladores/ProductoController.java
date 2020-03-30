package com.joseluisgs.productosapirest.controladores;


import com.fasterxml.jackson.annotation.JsonView;
import com.joseluisgs.productosapirest.configuracion.APIConfig;
import com.joseluisgs.productosapirest.dto.CreateProductoDTO;
import com.joseluisgs.productosapirest.dto.EditProductoDTO;
import com.joseluisgs.productosapirest.dto.GetProductoDTO;
import com.joseluisgs.productosapirest.dto.coverter.ProductoDTOConverter;
import com.joseluisgs.productosapirest.errores.ApiError;
import com.joseluisgs.productosapirest.errores.excepciones.ProductoBadRequestException;
import com.joseluisgs.productosapirest.errores.excepciones.ProductoNotFoundException;
import com.joseluisgs.productosapirest.errores.excepciones.SearchProductoNoResultException;
import com.joseluisgs.productosapirest.modelos.Producto;
import com.joseluisgs.productosapirest.servicios.CategoriaService;
import com.joseluisgs.productosapirest.servicios.ProductoService;
import com.joseluisgs.productosapirest.utilidades.paginacion.PaginationLinksUtils;
import com.joseluisgs.productosapirest.utilidades.vistas.ProductoView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

// Indicamos que es un controlador de tipo Rest
@RestController

// Esta va a ser la raiz de donde escuchemos es decir http://localhost/api/productos/
// Cuidado que se necesia la barra al final porque la estamos poniendo en los verbos
@RequestMapping(APIConfig.API_PATH+"/productos") // Sigue escucnado en el directorio API

// Si ponemos esta anotación no es necesario el @Autowired, si lo ponemos no pasa nada,
@RequiredArgsConstructor

public class ProductoController {


    //@Autowired // No es necesario el @Autowired por la notacion @RequiredArgsConstructor, pero pon el final
    private final ProductoService productoService;
    private final ProductoDTOConverter productoDTOConverter;
    private final PaginationLinksUtils paginationLinksUtils;
    private final CategoriaService categoriaService;

    /**
     * Lista todos los productos
     *
     * @return 404 si no hay productos, 200 y lista de productos si hay uno o más
     */

    //@CrossOrigin(origins = "http://localhost:8888") // No es necesario porque ya tenemos las conf globales MyConfig
    // Indicamos sobre que puerto u orignes dejamos que actue (navegador) En nuestro caso no habría problemas
    // Pero es bueno tenerlo en cuenta si tenemos en otro serviror una app en angular o similar
    // Pero es inviable para API consumida por muchos terceros. // Debes probar con un cliente desde ese puerto
    // Mejor hacer un filtro, ver MiConfiguracion.java
    @ApiOperation(value = "Obtiene una lista de productos", notes = "Obtiene una lista de productos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })

    // Implementación con busquedas con Specificación y Predicados PT4
    @JsonView(ProductoView.DTOConPrecio.class) // Indicamos que la tranformación sea con JSON View
    @GetMapping(value = "/")
    public ResponseEntity<?> buscarTodosConBusquedas(
            @RequestParam("nombre") Optional<String> txt,
            @RequestParam("precio") Optional<Float> precio,
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            HttpServletRequest request) {

        Page<Producto> result = productoService.findByArgs(txt, precio, pageable);

        if (result.isEmpty()) {
            throw new SearchProductoNoResultException();
        } else {

            // Con ModelMapper
            //Page<GetProductoDTO> dtoList = result.map(productoDTOConverter::convertToDto);
            // Con Lombok @Builder de DTO
            Page<GetProductoDTO> dtoList = result.map(productoDTOConverter::convertProdutoToProductoDTO);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

            return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                    .body(dtoList);

        }

    }


    /**
     * Obtiene un producto con un id específico
     *
     * @param id id del producto
     * @return 404 si no encuentra el producto, 200 y el producto si lo encuentra
     */
    @ApiOperation(value = "Obtener un producto por su ID", notes = "Provee un mecanismo para obtener todos los datos de un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{id}")
    public Producto obtenerProducto(@ApiParam(value = "ID del producto", required = true, type = "long") @PathVariable Long id) {
        // Excepciones con ResponseStatus
        try {
            return productoService.findById(id)
                    .orElseThrow(() -> new ProductoNotFoundException(id));
        } catch (ProductoNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }


    /**
     * Insertamos un nuevo producto
     *
     * @param nuevo nuevo producto a insertar
     * @return 201 y el producto insertado
     */
    @ApiOperation(value = "Crear un nuevo Producto", notes = "Provee la operación para crear un nuevo Producto a partir de un CreateProductoDto y devuelve el objeto creado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK", response = Producto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nuevoProducto(
            @ApiParam(value = "Datos del nuevo producto", type = "CreateProductoDTO.class")
            @RequestPart("nuevo") CreateProductoDTO nuevo,
            @ApiParam(value = "imagen para el nuevo producto", type = "application/octet-stream")
            @RequestPart("file") MultipartFile file) {

        try {

            // Comprobaciones
            if (nuevo.getNombre().isEmpty())
                throw new ProductoBadRequestException("Nombre", "Nombre vacío");
            if (nuevo.getPrecio() < 0)
                throw new ProductoBadRequestException("Precio", "Precio no puede ser negativo");

            // Con esto obligamos que todos producto pertenezca a una categoría si no quitar
            if (!categoriaService.findById(nuevo.getCategoriaId()).isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe o es vacía la categoría con ID: " + nuevo.getCategoriaId());


            return ResponseEntity.status(HttpStatus.CREATED).body(productoService.nuevoProducto(nuevo, file));

        } catch (ProductoNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }


    /**
     * Editamos un producto
     *
     * @param editar producto a editar
     * @param id     id del producto a editar
     * @return 200 Ok si la edición tiene éxito, 404 si no se encuentra el producto
     */
    @ApiOperation(value = "Edita un Producto", notes = "Edita un producto en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PutMapping("/{id}")
    public Producto editarProducto(@RequestBody EditProductoDTO editar, //Producto editar Sin DTO
                                   @PathVariable Long id) {

        // Comprobamos que los campos no sean vacios antes o el precio negativo
        if (editar.getNombre().isEmpty())
            throw new ProductoBadRequestException("Nombre", "Nombre vacío");
        else if (editar.getPrecio() <= 0)
            throw new ProductoBadRequestException("Precio", "Precio no puede ser negativo");
        else {
            // Se puede hacer con su asignaciones normales sin usar map, mira nuevo
            // Ahora con el DTO lo que hacemos como lógica de negocio es que no pueda cambiarse la categoría
            // Porque lo filtramos la categoría
            return productoService.findById(id).map(p -> {
                p.setNombre(editar.getNombre());
                p.setPrecio(editar.getPrecio());
                return productoService.save(p);
            }).orElseThrow(() -> new ProductoNotFoundException(id));
        }

    }

    /**
     * Borra un producto con un id espcífico
     *
     * @param id id del producto a borrar
     * @return Código 204 sin contenido
     */
    @ApiOperation(value = "Elimina un Producto", notes = "Elimina un producto en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "OK", response = Producto.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Long id) {

        // Con manejo de excepciones
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        productoService.delete(producto);
        return ResponseEntity.noContent().build();
    }
}
