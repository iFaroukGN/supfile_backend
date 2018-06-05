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
import org.supinf.entities.User;
import org.supinf.security.AbstractUserDetails;
import org.supinf.service.IAuthenticationService;
import org.supinf.webapi.AuthenticationResponse;
import org.supinf.entities.projection.UserWithoutPassword;

/**
 * Une implémentation du service d'authentification
 *
 * @author BLU Kwensy Eli
 */
@Component
public class DefaultAuthenticationService implements IAuthenticationService {

    @Autowired
    private UserService userService;
    /**
     * Injection instance AuthenticationManager.
     *
     * @see JwtWebSecurityConfigurerAdapter#authenticationManagerBean()
     */
    @Autowired
    @Qualifier("defaultAuthenticationManager")
    private AuthenticationManager authenticationManager;

    /**
     * @see IAuthenticationService#authenticate(java.lang.Object,
     * java.lang.Object)
     * https://docs.spring.io/spring-security/site/docs/5.0.3.RELEASE/reference/htmlsingle/#what-is-authentication-in-spring-security
     */
    @Override
    public Object authenticate(Object username, Object password) {
        //on appelle la méthode d'authenfication de Spring Security
        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // insertion dans la contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    /**
     *
     * @return
     */
    @Override
    public UserWithoutPassword getAuthenticatedUser() {
        Long userId = ((AbstractUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User authenticatedUser = userService.findOne(userId);
        return new UserWithoutPassword() {
            @Override
            public Long getId() {
                return authenticatedUser.getId();
            }

            @Override
            public String getUsername() {
                return authenticatedUser.getUsername();
            }

            @Override
            public String getEmail() {
                return authenticatedUser.getEmail();
            }

            @Override
            public String getFacebookEmail() {
                return authenticatedUser.getFacebookEmail();
            }

            @Override
            public String getGoogleEmail() {
                return authenticatedUser.getGoogleEmail();
            }
        };
    }

}
