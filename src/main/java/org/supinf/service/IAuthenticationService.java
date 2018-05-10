/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.supinf.service;

import org.springframework.security.core.Authentication;

/**
 * une interface qui permet de s'authetifier avec Spring Security
 * @author BLU Kwensy Eli
 */
public interface IAuthenticationService {
    
    /**
     * Cette méthode permet de s'authentifier grâce à un nom d'utilisateur et un mot de passe
     * @param username
     * @param password
     * @return 
     */
    public Object authenticate(Object username, Object password);
}
