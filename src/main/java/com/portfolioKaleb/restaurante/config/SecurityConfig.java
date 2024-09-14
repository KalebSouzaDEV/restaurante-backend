package com.portfolioKaleb.restaurante.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private Resource publicKeyResource;

    @Value("${jwt.private.key}")
    private Resource privateKeyResource;

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;


    @PostConstruct
    public void init() throws Exception {
        System.out.println("VAI SI FUDERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: \n" + publicKeyResource + "\n " + privateKeyResource + "\n VAI SI FUDERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        this.publicKey = loadPublicKey();
        System.out.println("222TROCARIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:: \n" + this.publicKey  + "\n" + this.privateKey);

        this.privateKey = loadPrivateKey();
        System.out.println("TROCARIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:: \n" + this.publicKey  + "\n" + this.privateKey);
    }

    private RSAPublicKey loadPublicKey() throws Exception {
        System.out.println("public1");
        byte[] keyBytes = Base64.getDecoder().decode("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZnRv5SRhz8c4AsbeDiOl6f3QnATty+I2yaJ4eSt3dYJ80BzGz511vQI/cVGmp4X6wVyQeiwyvBsHS7FOjIHD/rK+El9urhNc10YbPRa+RTrBg6I+XkDunwEJtFfevgA6YM4GVSunC/mVLXYLcee/me+BPRGYn1DZoeNG/l/YhdTuEZLop7bxMvVAqdtw/BzFZo7q1IJlMQQlp5Gmv8+rVDOjqxfvVj36g3EZZ/EOqzT99cIv2U9eFS+StMk7gSfSv71qLw9Vgblx5m5tv8wNjepSPkThksw8/whI5gEZRXE+A+/QYzjQk6rEuSoaA3zBaHlswRwuTk0YUH5OJlvMVAgMBAAECggEAJgS4U64/mxKRhaJ2ZNuRzXuV58FqUT4q86jo2V52IsAzJtFQ/9GXUl2MPV+29xCUJliq29RWQ0tM1UMgQBNbZCv9FLfw6jZqfStqTgpVNYVgKdvlDgHogdAXoJ/7OThcZFb7D1QHHvePYmO2a5LSSR0Hmxa2oczn2ltsrhqSFB1pfwa3YpLqf/jXVj2qJGLw8Jq7/QW5ad53Do9NB83SaJb58WVhAFSoPpMU51sS/GwGGX04j57MoA6SqRXFnL0TKAOJ2AMSoc72SCder1+BjyKjHI6lOt3CqIWOnqEDw8Xsw+cnl1K9Y1vU73KxReAW6OOg4DXOUofdUQUcNZdUUQKBgQDIevUWK8MG7ZXm5A2zSkRwcH6xnh5klrgeJDlUGqooHXbpv9++rWWexdEh1W6bM7KnrNIC+JWQp8QPaIpKagURXruqvxiWmaXjN1cBSmxNOfGp5/lppUovyRKPVuJeVPnGtTDHaLXoXqmJiiMkT6VQJZZaIbGIbYafjdqh+78GxwKBgQDEJ4vy4yxVPUMzP6PNsrUoxRn6YkxIyp9xSPohyCCgdijNOW4v73HmHinVP61ADZAa2loxqpvNizgeILo4URroKUKOeRFZ2J/QteZNJT02FsgTg4RVsy2RZYKjVQSFXIIXgi7eLP8IMKz6xAqVgnSTfAUKZy3PR8pdRwkzjCVrQwKBgDZha/O8oSVS8vNzbufwQEpDqYoietoMLBs49jgDF57UBzp2qol9XXBI9mZ4D42X42EqNrlJlsBrUXpggAqZYZCJotw5A8FuIhpVG4ulFWWJTy0oWHY4uLvnjdpF1heCc1JcOcQNejJy4wgE6W1jEVOBLpx6QT7hfr/u6ZxAvltnAoGARRUMc8FQNsD5rs3l+abLqI0qJgWCikxaNQKXnVgnLXwPyJ9GUDmLKplK8GCZuqpMKzjldQJ6FrzGSc4K55Sg3kzRtO2sj7D8L/wwHNFVqnwBgOXbsHcHQGK4zJ8/lVxMMM1XhkXgl9jUwwhSFe+48MxH1i8achr9iTZgZjggYZMCgYAJ8SiPgg1EJP+RZfB1O2NgTJQ9JuGwoUxJZldaZdIZuoxHMcyZKXZpj5DDxm5zsTbrCZ30aushFEL8CbsV43lw6jLp5QbpJBYq2GbC6Agqpr0WJo/2e66q4l+kRL3bETh2UcH1RYsKi6IfOlx2NefkdsVsBZA2z+cEOkoPNQfkTA==");
        System.out.println("public3");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        System.out.println("public4");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        System.out.println("public5: " +  keyFactory.generatePublic(spec));
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    private RSAPrivateKey loadPrivateKey() throws Exception {
        System.out.println("private3");
        byte[] keyBytes = Base64.getDecoder().decode("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmZ0b+UkYc/HOALG3g4jpen90JwE7cviNsmieHkrd3WCfNAcxs+ddb0CP3FRpqeF+sFckHosMrwbB0uxToyBw/6yvhJfbq4TXNdGGz0WvkU6wYOiPl5A7p8BCbRX3r4AOmDOBlUrpwv5lS12C3Hnv5nvgT0RmJ9Q2aHjRv5f2IXU7hGS6Ke28TL1QKnbcPwcxWaO6tSCZTEEJaeRpr/Pq1Qzo6sX71Y9+oNxGWfxDqs0/fXCL9lPXhUvkrTJO4En0r+9ai8PVYG5ceZubb/MDY3qUj5E4ZLMPP8ISOYBGUVxPgPv0GM40JOqxLkqGgN8wWh5bMEcLk5NGFB+TiZbzFQIDAQAB");
        System.out.println("private4");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        System.out.println("private5");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        System.out.println("private6");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(withDefaults()) // Configura o CORS
                .authorizeHttpRequests(authorize -> authorize
                        /*.requestMatchers( HttpMethod.POST,"/login").permitAll()
                        .requestMatchers( HttpMethod.POST,"/users").permitAll()
                        .requestMatchers( HttpMethod.GET,"/categories").permitAll()
                        .requestMatchers( HttpMethod.PUT,"/categories").permitAll()
                        .requestMatchers( HttpMethod.DELETE,"/categories").permitAll()
                        .requestMatchers( HttpMethod.POST,"/categories").permitAll()
                        .requestMatchers( HttpMethod.GET,"/product/categorie/**").permitAll()
                        .requestMatchers( HttpMethod.POST,"/product").permitAll()
                        .requestMatchers( HttpMethod.GET,"/product").permitAll()
                        .requestMatchers( HttpMethod.GET,"/users").hasAuthority("SCOPE_admin")
                        .anyRequest().authenticated())*/
                        .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Adiciona a URL permitida
        configuration.setAllowedMethods(List.of("*")); // Permite todos os métodos HTTP
        configuration.setAllowedHeaders(List.of("*")); // Permite todos os cabeçalhos
        configuration.setAllowCredentials(true); // Permite credenciais (cookies, headers de autorização)
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                return configuration;
            }
        };
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
