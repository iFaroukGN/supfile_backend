package org.supinf.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * Filtre utilisée dans la configuration de sécurité et s'intégrant dans la
 * FilterChain de SpringSecurity pour intercepter les requêtes et contrôler les
 * entêtes d'autorisation
 *
 * @see
 * JwtWebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
 *
 * @author BLU Kwensy Eli
 */
@Component
public class JwtAuthorizationFilter extends GenericFilterBean {

    /**
     *
     */
    public static final String HEADER = "Authorization";

    /**
     *
     */
    public static final String PREFIX = "Bearer ";
    /**
     * Injection instance JwtUtils
     */
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * cette méthode permet d'intercepter toutes les URL sécurisées, vérifier
     * leur validité (existence d'en-têtes de sécurité, ...), et la validité du
     * jeton JWT
     *
     * @see GenericFilterBean#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(HEADER);
        if (header == null || !header.startsWith(PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = getAuthentication(req);
        //On effectue une authentification explicite en intégrant ...
        //...  un objet de type Authentication dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * Renvoie une classe de type Authentication construite à partir des
     * informations contenues dans le jeton
     *
     * @param request
     * @return
     */
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        
        if (token != null) {
            // on décode le jeton
            AbstractUserDetails user = jwtUtils
                    .parseToken(token.replace(PREFIX, ""));

            // une instance UsernamePasswordAuthenticationToken ...
            //... se rapproche un peu de notre cas de figure
            if (user != null) {
                //utiliser obligatoirement ce constructeur pour la classe UsernamePasswordAuthenticationToken ...
                // .. pour obtenir une instance valide [isAuthenticated = true] de la classe
                Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }

}
