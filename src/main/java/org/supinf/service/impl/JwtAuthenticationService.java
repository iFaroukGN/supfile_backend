/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.supinf.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.supinf.config.JwtWebSecurityConfigurerAdapter;
import org.supinf.security.AbstractUserDetails;
import org.supinf.security.JwtUtils;
import org.supinf.service.IAuthenticationService;
import org.supinf.webapi.AuthenticationResponse;

/**
 * Une implémentation du service d'authentification
 *
 * @author BLU Kwensy Eli
 */
@Component
public class JwtAuthenticationService extends DefaultAuthenticationService {

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Cette méthode permet de s'authentifier comme le définit Spring Security
     * et génère un jeton JWT
     */
    @Override
    public Object authenticate(Object username, Object password) {
        //on appelle la méthode d'authenfication de Spring Security
        Authentication authentication = (Authentication) super.authenticate(username, password);
        String token = jwtUtils.generateToken((AbstractUserDetails) authentication.getPrincipal());
        Long connectedUserId = ((AbstractUserDetails) authentication.getPrincipal()).getId();
        return new Pair<Long, String>(connectedUserId, token);
    }

}
