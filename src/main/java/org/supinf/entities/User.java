/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * adresse mail dauthentification
     */
    @Column(unique = true)
    private String email;
    /**
     * Mot de passe d'authentification
     */
    private String password;

    /**
     * Constructeur avec arguments
     *
     * @param email
     * @param password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
