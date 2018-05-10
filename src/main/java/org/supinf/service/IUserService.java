package org.supinf.service;

import org.supinf.entities.User;
import java.util.List;
import org.supinf.dao.UserRepository;

/**
 *
 * @author BLU Kwensy Eli
 */
public interface IUserService {

    /**
     * Créer un utilisateur
     *
     * @param user
     * @return l'entité créée
     */
    public User save(User user);

    /**
     * Rechercher un utilisateur grâce à son identifiant
     *
     * @param id
     * @return l'entité recherchée
     */
    public User findOne(Long id);

    /**
     * Mettre à jour un utilisateur
     *
     * @param user
     * @return L'entité avec ses attributs màj
     */
    public User update(User user);

    /**
     * Rechercher tous les utilisateurs
     *
     * @return Toutes les instances de l'entité
     */
    public List<User> findAll();

    /**
     * Supprimer un utilisateur
     *
     * @param user
     */
    public void delete(User user);

    /**
     * @see UserRepository#findByEmail(java.lang.String)
     */
    public User findByEmail(String email);

    /**
     * @see UserRepository#findByFacebookEmail(java.lang.String)
     */
    public User findByFacebookEmail(String facebookEmail);
}
