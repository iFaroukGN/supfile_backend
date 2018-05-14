/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.supinf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supinf.service.IAuthenticationService;
import org.supinf.webapi.AuthenticationRequest;
import org.supinf.webapi.AuthenticationResponse;

/**
 *
 * @author BLU Kwensy Eli
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    @Qualifier("jwtAuthenticationService")
    IAuthenticationService authenticationService;

    /**
     * Url permettant de se connecter grâce aux informations fournies
     *
     * @param authenticationRequest
     * @see AuthenticationRequest
     * @return
     */
    @PostMapping("/login/default")
    public ResponseEntity<AuthenticationResponse> defaultLogin(@RequestBody AuthenticationRequest authenticationRequest) {
        try {

            // Contenu du corps de réponse
            AuthenticationResponse response = new AuthenticationResponse();

            //email
            String email = authenticationRequest.getEmail();
            String password = authenticationRequest.getPassword();

            // authentification
            String token = (String)authenticationService.authenticate(email, password);
            
            
            //on verifie que l'authetification s'est bien passée en allant chercher les informations dans le contexte de sécurité
            //String authenticatedUsername = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            //
            response.setToken(token);
            


            // On renvoie la réponse
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     *
     * Url permettant de se déconnecter
     */
    @GetMapping("/logout")
    public void logout() {
    }

}
