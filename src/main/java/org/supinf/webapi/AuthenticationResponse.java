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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
