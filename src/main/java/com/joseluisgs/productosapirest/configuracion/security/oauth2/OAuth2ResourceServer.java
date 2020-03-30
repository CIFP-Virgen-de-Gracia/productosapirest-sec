package com.joseluisgs.productosapirest.configuracion.security.oauth2;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        resources.resourceId("oauth2-resource"); // Recurso al que accedemos

    }

    @Override
    // Política de seguridad
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .sessionManagement()//Sin sesión
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin sesión
                .and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/oauth/token").permitAll()

                // Sirve para habilitar la consola de H2
                .antMatchers("/h2-console/**").permitAll() // Quitar en producción

                // Registrarse todos
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/usuarios/**").permitAll()

                // Consultar pedido y lotes, usuarios registrados
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/productos/**",
                        APIConfig.API_PATH + "/lotes/**").hasRole("USER")

                // añadir, modificar o borrar pdocutso y lotes, solo admin
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/productos/**",
                        APIConfig.API_PATH + "/lotes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/productos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/productos/**").hasRole("ADMIN")

                // añadir pedidos, usuarios registrados
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/pedidos/**").hasAnyRole("USER", "ADMIN")

                // Resto de rutas, auteticadas
                .anyRequest().authenticated();

        // Sirve para habilitar la consola de H2
        http.headers().frameOptions().disable();


    }


}
