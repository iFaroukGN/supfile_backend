package org.supinf.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Classe encapsulent les informations complémentaires ( à mapper aux champs
 * de la classe de domaine <code>org.supinf.entities.user</code> ) à insérer
 * dans le contexte de sécurité
 *
 * @author BLU Kwensy Eli
 */
public class AbstractUserDetails extends User {

    /**
     * L'identifiant de l'utilisateur
     */
    protected Long id;

    public AbstractUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AbstractUserDetails( Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities);
        this.id = id;
    }

    /**
     * Getter
     * @return 
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    

}
