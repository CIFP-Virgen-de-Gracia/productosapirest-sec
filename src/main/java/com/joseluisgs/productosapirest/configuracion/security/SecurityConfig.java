package com.joseluisgs.productosapirest.configuracion.security;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import com.joseluisgs.productosapirest.configuracion.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// Activamos la seguridad a nivel de método, por si queremos trabajar a nivel de controlador
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    /*
    Mecanismos de autentificación
    Expone nuestro mecanismos de autentificación como un bean para que luego lo podamos usar en un filtro
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    /*
    Configuración de autorización según verbos y rutas
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                // Para el establecimiento de sesiones son estado, no usamos sesiones
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Autorizamos con roles y acceso
                .authorizeRequests()

                // Registrarse todos
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/usuarios/**").permitAll()

                // Loguearse
                //.antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/auth/login").permitAll()

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

        // Será el encargado de coger el token y si es válido lo dejaremos pasar...
        // Añadimos el filtro (jwtAuthorizationFilter).
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);


    }


}
