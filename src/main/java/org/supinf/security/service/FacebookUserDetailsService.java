package org.supinf.security.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.supinf.entities.User;
import org.supinf.service.impl.UserService;

/**
 * Classe permettant de fournir au contexte de sécurité un utilisateur recherché
 * grâce à son email Facebook
 *
 * @author BLU Kwensy Eli
 */
@Component
public class FacebookUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //utilisteur dans la base de données
        User dataSourceUser = userService.findByFacebookEmail(email);
        /**
         * sil'utilisateur n'existe pas
         */
        if (dataSourceUser == null) {
            throw new UsernameNotFoundException(email);
        }

        // on retourne un utilisateur comme défini par Spring Security
        return new org.springframework.security.core.userdetails.User(
                email,
                null,
                Collections.EMPTY_LIST);
    }
}
