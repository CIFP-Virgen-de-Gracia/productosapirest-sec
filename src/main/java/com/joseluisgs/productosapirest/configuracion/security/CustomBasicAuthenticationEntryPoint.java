package com.joseluisgs.productosapirest.configuracion.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseluisgs.productosapirest.errores.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    // Trasformamos un objeto JSON en una cadena de caracteres
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException /*, ServletException*/ {

        // Encabezado con el nombre del reino
        response.addHeader("WWW-Authenticate", "Basic realm='" + getRealmName() + "'");
        // Los errores se devuelvan en JSON
        response.setContentType("application/json");
        //HTTP Status en vez de los de JAVAEE
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // Si no estamos autrizados lanzamos nuestro error
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage());
        // Lo transforamos en una cadena de caracteres
        String strApiError = mapper.writeValueAsString(apiError);
        // Obtenemos la respuesta y escribimos
        PrintWriter writer = response.getWriter();
        writer.println(strApiError);


    }

    // Nombre de Reino de Seguridad
    @PostConstruct
    public void initRealname() {
        setRealmName("https://twitter.com/joseluisgonsan");
    }
}
