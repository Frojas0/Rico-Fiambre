package com.ricofiambre.ecomerce.configuraciones;

import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AutenticacionWeb extends GlobalAuthenticationConfigurerAdapter  {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email-> {
            Cliente cliente = clienteRepositorio.findByEmail(email);
            if (cliente != null) {
                if (cliente.getEmail().equalsIgnoreCase("admin@admin.com")){
                    return new User(cliente.getEmail(), cliente.getContrasena(),
                            AuthorityUtils.createAuthorityList("ADMINISTRADOR"));
                } else {
                    return new User(cliente.getEmail(), cliente.getContrasena(),
                            AuthorityUtils.createAuthorityList("CLIENTE"));
                }
            } else {
                throw new UsernameNotFoundException("Unknown user: " + email);
            }
        });
    }
    @Bean
    public PasswordEncoder passwordEncoder() {return PasswordEncoderFactories.createDelegatingPasswordEncoder();}

}


