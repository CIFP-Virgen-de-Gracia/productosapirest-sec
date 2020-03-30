package com.joseluisgs.productosapirest.configuracion.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    // Los beans que necesitamos
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    // Constantes de ocnfiguración de OAuth2
    @Value("${oauth2.client-id}") // Un solo cliente (deberíamos tener mas
    private String clientId;

    @Value("${oauth2.client-secret}") // un secreto
    private String clientSecret;

    @Value("${oauth2.redirect-uri}") // una URI
    private String redirectUri;

    @Value("${oauth2.access-token-validity-seconds}") // tiempo de valido de token
    private int accessTokenValiditySeconds;

    @Value("${oauth2.access-token-validity-seconds}") // tiempo de acceso
    private int refreshTokenValiditySeconds;

    private static final String CODE_GRANT_TYPE = "authorization_code";
    private static final String IMPLICIT_GRANT_TYPE = "implicit";
    private static final String PASS_GRANT_TYPE = "password";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    @Override
    // Algunos elementos de segurdiad
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") //Cualquiera podrá tener acceso al servicio de tokenkey
                .checkTokenAccess("isAuthenticated()") // Pero si queremos acceder al chek debe estar registrado
                .allowFormAuthenticationForClients(); // Permitimos acceder al formulario de acceso

    }

    @Override
    // Configuración de Autorización
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory() // Por ahora trabajamos en en memoria
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret)) // Secreto cifrado
                // Permisos
                .authorizedGrantTypes(PASS_GRANT_TYPE, REFRESH_TOKEN_GRANT_TYPE,
                        IMPLICIT_GRANT_TYPE, CODE_GRANT_TYPE)
                .authorities("READ_ONLY_CLIENT") // Cliente de solo lectura
                .scopes("read") // Genérico de lectura
                .resourceIds("oauth2-resource") // Podemos usar properties
                .redirectUris(redirectUri) // URI de redirección
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);


    }

    @Override
    // Enpoint: Autenticación y UserDetails
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }

}
