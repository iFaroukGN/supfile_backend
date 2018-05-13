package org.supinf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.supinf.entities.User;

/**
 * Classe utilitaire Jwt
 * https://www.toptal.com/java/rest-security-with-jwt-spring-security-and-java
 *
 * @author Supfile
 */
@Component
public class JwtUtils {

    /**
     * la durée de validité d'un jeton
     */
    @Value("${jwt.lifetime}")
    private Long lifetime;

    /**
     * la clé utilisée pour encoder le jeton
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * l'entité qui génère le token ici notre application
     */
    @Value("${spring.application.name}")
    private String issuer;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns
     * User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user
     * properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token
     * is invalid.
     */
    public AbstractUserDetails parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            // le jeton est valide si  ...
            // ... la valeur du champ isssuer est la meme qu'à la génération
            if (!issuer.equals(body.getIssuer())) {
                throw new JwtException("Invalid issuer");
            }
            // ... la durée de vie du jeton est la même que lorsque le jeton a été généré
            Date issuedAt = body.getIssuedAt();
            Date expiration = body.getExpiration();
            if(!lifetime.equals(expiration.getTime() - issuedAt.getTime())){
                throw new JwtException("Invalid token lifetime");
            }
            
            String userName = body.getSubject();
            Long userId = Long.parseLong((String) body.get("userId"));
            Collection authorities = (Collection) body.get("authorities");

            return new AbstractUserDetails(userId, userName, "", authorities);

        } catch (JwtException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role
     * as additional claims. These properties are taken from the specified User
     * object.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(AbstractUserDetails u) {
        // date courante
        Long now = System.currentTimeMillis();
        // Date d'expiration
        Long expirationDate = now + lifetime;

        Claims claims = Jwts.claims()
                .setSubject(u.getUsername())
                //l'entité qui a créé le jeton
                .setIssuer(issuer)
                // la date de création du jeton
                .setIssuedAt(new Date(now))
                // la date d'expiration du jeton;
                .setExpiration(new Date(expirationDate));

        // informations aditionnelles 
        claims.put("userId", u.getId() + "");
        claims.put("authorities", u.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
