package org.supinf.webapi;

/**
 * Classe encapsulant les informations envoyées par les applications clientes pour
 * s'authentifier via une requête HTTP
 *
 * @author BLU Kwensy Eli
 */
public class AuthenticationRequest {

    /**
     * email
     */
    protected String email;

    /**
     * password
     */
    protected String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
