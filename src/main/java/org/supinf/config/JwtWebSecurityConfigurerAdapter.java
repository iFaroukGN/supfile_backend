package org.supinf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.supinf.service.impl.DefaultUserDetailsService;

/**
 * Classe de configuration pour la sécurité
 *
 * @author Supfile
 */
@Configuration
@EnableWebSecurity
public class JwtWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    /**
     * Injection instance userDefaultService
     */
    @Autowired
    DefaultUserDetailsService defaultUserDetailsService;

    /**
     * @see WebSecurityConfigurerAdapter#authenticationManagerBean()
     * @return
     * @throws Exception
     */
    @Bean(name = "defaultAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * On crée un bean qui va se charger de chiffrer les mots de passe. Il
     * pourra être utilisé dans les configurations ou méthodes qui nécessitent
     * la manipulation de mots de passe
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de l'<code>AuthenticationProvider</code>
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(defaultUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * Configuration de la sécurité HTTP
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .cors() // activation du support CORS
                .formLogin().disable() // désactivation du formulaire de connexion par défaut
                .csrf().disable() // désactivation dela protection CSRF
                .httpBasic().disable() // désactivation de l'authentification basique
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login/**").permitAll() // on autorise uniquement les requetes POST pour l'URL indiquée
                .antMatchers(HttpMethod.POST, "/users/").permitAll()
                .anyRequest() // pour toutes les autres URLs ...
                .authenticated() // ... une authentification requise
                .and()
                // désactivation de la creation de session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
