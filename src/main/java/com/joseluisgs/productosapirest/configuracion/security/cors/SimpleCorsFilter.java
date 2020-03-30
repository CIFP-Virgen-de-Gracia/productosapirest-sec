package com.joseluisgs.productosapirest.configuracion.security.cors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Prioridad más alta. Tenga más prioridad que cualquier otro
public class SimpleCorsFilter implements Filter {

    @Override
    // Creación del filtro
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // Casteamos
        final HttpServletResponse response = (HttpServletResponse) res;
        //Añadimos la cabecera, no matizamos los origenes, por eso *
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Añadimos la cabecera a los métodos que permitimos: verbos HTTP
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        // Permitimos los encabezados permitidos.
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        // Añadimos la vida máxima
        response.setHeader("Access-Control-Max-Age", "3600");
        // Comprobamos si el métodp es options, se usa mucho en Angular
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}