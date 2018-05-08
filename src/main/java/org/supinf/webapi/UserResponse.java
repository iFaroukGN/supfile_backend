
package org.supinf.webapi;

/**
 * Classe encapsulant les informations sur un utilisateur en réponse à une requête HTTP
 * @author BLU Kwensy Eli
 */
public class UserResponse {
    /**
     * L'identifiant de l'entité
     */
    protected Long id;

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
