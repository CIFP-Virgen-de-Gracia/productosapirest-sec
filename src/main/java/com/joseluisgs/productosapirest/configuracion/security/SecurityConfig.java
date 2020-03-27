package com.joseluisgs.productosapirest.configuracion.security;

import com.joseluisgs.productosapirest.configuracion.APIConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Punto de entrada
    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    //Servicio de detalle de usuario del Spring
    private final UserDetailsService userDetailsService;
    // Codificador de password
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }


    /**
     * Configuramos toda la política de seguridad
     * según perfiles, rutas y verbos HTTP
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(customBasicAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                // Peticiones de consulta: Todos
                .antMatchers(HttpMethod.GET, APIConfig.API_PATH + "/productos/**", APIConfig.API_PATH + "/lotes/**").hasRole("USER")
                // Peticiones de añadir,actualizar o borrar producto: Admin
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/productos/**", APIConfig.API_PATH + "/lotes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, APIConfig.API_PATH + "/productos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, APIConfig.API_PATH + "/productos/**").hasRole("ADMIN")
                // Peticiones de añadir, actualizar o borrar pedido: todos
                .antMatchers(HttpMethod.POST, APIConfig.API_PATH + "/pedidos/**").hasAnyRole("USER", "ADMIN")
                // Otras peticiones, deben autenticarse
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }


}
