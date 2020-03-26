package com.joseluisgs.productosapirest.errores;

import com.joseluisgs.productosapirest.errores.excepciones.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice

// Para controlar mis excepciones
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
    // Array de excpeciones
    // Producto no encotrado, busqueda sin resultados
    // Producto no encotrado
    // Categoría no encotrada
    // Busqueda no encontrada
    // Pedido no encotrado
    // Lote no encotrado
    @ExceptionHandler({ProductoNotFoundException.class, ProductoNotFoundException.class,
            CategoriaNotFoundException.class, SearchProductoNoResultException.class, PedidoNotFoundException.class,
            LoteNotFoundException.class})
    public ResponseEntity<ApiError> handleNoEncontrado(Exception ex) {
        // Aplicamos el nuevo constructor indicado an APIREST
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    // Producto creación incorrecta
    @ExceptionHandler(ProductoBadRequestException.class)
    public ResponseEntity<ApiError> handleProductoPeticionIncorrecta(ProductoBadRequestException ex) {
        // Aplicamos el nuevo constructor indicado an APIREST
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    // Lote creación incorrecta
    @ExceptionHandler(LoteCreateException.class)
    public ResponseEntity<ApiError> handleLoteCreacionIncorrecta(LoteCreateException ex) {
        // Aplicamos el nuevo constructor indicado an APIREST
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


    // Clase para controlar errores inesperados que no estamos teniendo en cuenta
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(status, ex.getMessage());
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }




}
