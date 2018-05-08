/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.supinf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.supinf.config.JwtWebSecurityConfigurerAdapter;
import org.supinf.service.IAuthenticationService;
import org.supinf.webapi.AuthenticationResponse;

/**
 * Une implémentation du service d'authentification
 * @author BLU Kwensy Eli
 */
@Component
public class DefaultAuthenticationService implements IAuthenticationService {

    /**
     * Injection instance AuthenticationManager.
     * @see JwtWebSecurityConfigurerAdapter#authenticationManagerBean() 
     */
    @Autowired
    @Qualifier("defaultAuthenticationManager")
    AuthenticationManager authenticationManager;

    /**
     * @see IAuthenticationService#authenticate(java.lang.Object,
     * java.lang.Object)
     * https://docs.spring.io/spring-security/site/docs/5.0.3.RELEASE/reference/htmlsingle/#what-is-authentication-in-spring-security
     */
    @Override
    public Authentication authenticate(Object username, Object password) {
        //on appelle la méthode d'authenfication de Spring Security
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // insertion dans la contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

}
