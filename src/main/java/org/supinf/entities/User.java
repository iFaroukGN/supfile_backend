package org.supinf.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe représentant un utilisateur
 *
 * @author Supfile
 */
@Entity
@Data
@NoArgsConstructor
public class User {

    /**
     * Identifiant en base de données
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * adresse mail par défaut
     */
    @Column(unique = true, nullable = true)
    private String email;

    /**
     * adresse mail facebook
     */
    @Column(unique = true, nullable = true)
    private String facebookEmail;
    /**
     * adresse mail google
     */
    @Column(unique = true, nullable = true)
    private String googleEmail;
    /**
     * Mot de passe d'authentification
     */
    private String password;
    /**
     * nom d'utilisateur
     */
    private String username;

    /**
     * Constructeur avec arguments
     *
     * @param email
     * @param facebookEmail
     * @param googleEmail
     * @param password
     * @param username
     */
    public User(String email, String facebookEmail, String googleEmail, String password, String username) {
        this.email = email;
        this.facebookEmail = facebookEmail;
        this.googleEmail = googleEmail;
        this.password = password;
        this.username = username;
    }
}
