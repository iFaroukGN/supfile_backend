package org.supinf.webapi;

import org.springframework.security.core.Authentication;

/**
 * Classe encapsulant les informations envoyées en réponse HTTP après une
 * authentification réussie
 *
 * @author BLU Kwensy Eli
 */
public class AuthenticationResponse {

    /**
     * un jeton d'authentification
     */
    protected String token;

    /**
     * l'identifiant de l'utilisateur connecté
     */
    protected Long id;

    /**
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

}
