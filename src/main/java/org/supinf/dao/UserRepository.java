package org.supinf.dao;

import org.supinf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.supinf.entities.projection.UserWithoutPassword;

/**
 *
 * @author BLU Kwensy Eli
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrouver un utilisateur grâce à son mail
     *
     * @param email
     * @return l'entité recherchée
     */
    public User findByEmail(String email);

    /**
     *
     * Retrouver un utilisateur grâce à son mail facebook
     *
     * @param facebookEmail
     * @return
     */
    public User findByFacebookEmail(String facebookEmail);
}
