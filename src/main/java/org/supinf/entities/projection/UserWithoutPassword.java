
package org.supinf.entities.projection;

/**
 * interface se projetant sur une partie des informations sur un utilisateur 
 * @author BLU Kwensy Eli
 */
public interface UserWithoutPassword {
    
    /**
     * 
     * @return 
     */
    public Long getId();

    /**
     * 
     * @return 
     */
    public String getUsername();
    

    /**
     * 
     * @return 
     */
    public String getEmail();
    
    /**
     * 
     * @return 
     */
    public String getFacebookEmail();
    
    /**
     * 
     * @return 
     */
    public String getGoogleEmail();
    
    
}
