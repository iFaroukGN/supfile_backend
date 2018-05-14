package org.supinf.security.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.supinf.entities.User;
import org.supinf.security.AbstractUserDetails;
import org.supinf.service.impl.UserService;

/**
 * Classe permettant de fournir au contexte de sécurité un utilisateur recherché
 * grâce à son email par défaut
 *
 * @author BLU Kwensy Eli
 */
@Component
public class DefaultUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    private UserService userService;

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

        // on retourne un utilisateur comme défini par Spring Security c'est-à-dire une implémentation de l'interface UserDetails
        return new AbstractUserDetails(
                dataSourceUser.getId(),
                dataSourceUser.getEmail(),
                dataSourceUser.getPassword(),
                Collections.EMPTY_LIST);
    }
}
