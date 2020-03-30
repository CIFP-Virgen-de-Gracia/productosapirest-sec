package com.joseluisgs.productosapirest.configuracion;

/*
Clase de configuración
 */

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
// Activamos la auditoria, esto por ejemplo nos permite no meter la fecha si no que la tome automáticamente
public class APIConfig {

    // Versión de la Api y versión del path, tomados de application.properties
    @Value("${api.path}.path")
    public static final String API_PATH = "/api";
    @Value("${api.version}")
    public static final String API_VERSION = "1.0";

    // Creamos el bean para el wrapper
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    /**
     * CORS: Configuración más ajustada. Nos la cargamos aquí y la llevamos
     * a seguridad donde creamos la clase SimpleCorsFilter.

     @Bean public WebMvcConfigurer corsConfigurer() {
     return new WebMvcConfigurer() {

     @Override
     // Ajustamos una configuración específica para cada serie de métodos
     // Así por cada fuente podemos permitir lo que queremos
     // Por ejemplo ene sta configuración solo permitirmos el dominio producto
     // Permitimos solo un dominio
            // e indicamos los verbos que queremos usar
            // Debes probar con uncliente desde ese puerto
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(APIConfig.API_PATH + "/productos/**")
                        .allowedOrigins("http://localhost:8888")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .maxAge(3600);
            }

        };
    }
     */
}

