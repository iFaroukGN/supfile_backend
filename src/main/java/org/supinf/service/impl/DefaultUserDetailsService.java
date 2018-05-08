package org.supinf.service.impl;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.supinf.entities.User;

/**
 * Classe permettant de fournir au contexte de sécurité un utilisateur recherché
 * grâce à son email
 *
 * @author BLU Kwensy Eli
 */
@Component
public class DefaultUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //utilisteur dans la base de données
        User dataSourceUser = userService.findByEmail(email);
        /**
         * sil'utilisateur n'existe pas
         */
        if (dataSourceUser == null) {
            throw new UsernameNotFoundException(email);
        }

        // on retourne un utilisateur comme défini par Spring Security
        return new org.springframework.security.core.userdetails.User(
                dataSourceUser.getEmail(),
                dataSourceUser.getPassword(),
                Collections.EMPTY_LIST);
    }
}
