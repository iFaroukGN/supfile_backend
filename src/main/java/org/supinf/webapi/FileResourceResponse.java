
package org.supinf.webapi;

/**
 * Classe encapsulant les informations sur un fichier en réponse à une requête HTTP
 * @author Edem Amegbeto
 */
public class FileResourceResponse {
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