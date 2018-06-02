package org.supinf.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant un partage de ressource
 *
 * @author Supfile
 */
@Entity
@Data
@NoArgsConstructor
public class UserResourceLink {

    /**
     * Identifiant en base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * type d'acces à la ressource partagée
     */
    @Column(nullable = false)
    private String accessType;

    /**
     * utilisateur pouvant consulter le lien de partage
     */
    @ManyToOne(optional = false)
    private User user;

    /**
     * La ressource partagée
     */
//    private Resource resource;
    
    
    /**
     * Constructeur avec arguments
     *
     * @param accessType
     */
    public UserResourceLink(String accessType) {
        this.accessType = accessType;
    }
}
