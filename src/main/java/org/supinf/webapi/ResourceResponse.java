
package org.supinf.webapi;

/**
 * Classe encapsulant les informations sur une resource en réponse à une requête HTTP
 * @author Edem Amegbeto
 * @author BLU Eli
 */
public class ResourceResponse {
    /**
     * L'identifiant de l'entité
     */
    protected Long id;

     /**
     * un message sur l'operation concernant la ressource
     */
    protected String message;
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
    
     public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResourceResponse(String message, Long id) {
        this.id = id;
        this.message = message;
    }
    
}
