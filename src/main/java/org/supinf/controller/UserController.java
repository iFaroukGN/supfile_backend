package org.supinf.controller;

import org.supinf.entities.User;
import org.supinf.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supinf.entities.FolderResource;
import org.supinf.io.storage.AbstractStorageAccessProvider;
import org.supinf.webapi.UserResponse;

/**
 * Classe controlleur
 *
 * @author BLU Kwensy Eli
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * injection instance StorageAccessProvider
     */
    @Autowired
    @Qualifier("localFileSystemIoHandler")
    AbstractStorageAccessProvider storageAccess;

    /**
     * injection instance BCryptPasswordEncoder
     *
     * @see JwtWebSecurityConfigurerAdapter#bCryptPasswordEncoder()
     */
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Injection d'une instance de IUserService
     */
    @Autowired
    IUserService userService;

    /**
     * Méthode définissant une ressource REST permettant de créer un utilisateur
     *
     * @param user
     * @return l'identifiant de l'utilisateur créé
     */
    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody User user) {
        //On chiffre le mot de passe avant de l'enregistrer
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //on construit une instance de réponse
        UserResponse response = new UserResponse();
        //on récupère les informations à envoyer
        response.setId(userService.save(user).getId());

        //initialisation de l'espace de stockage de l'utilisateur
        storageAccess.initUserStorageSpace(user);

        return ResponseEntity.ok(response);
    }

    /**
     * Méthode définissant une ressource REST permettant de retrouver un
     * utilisateur grâce à son identifiant
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    /**
     * Méthode définissant une ressource REST permettant de modifier un
     * utilisateur
     *
     * @param user
     * @return
     */
    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        //On chiffre le mot de passe avant de l'enregistrer
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return ResponseEntity.ok(userService.update(user));
    }

    /**
     * Méthode définissant une ressource REST permettant de retrouver la liste
     * de tous les utilisateurs
     *
     * @return La liste des utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Méthode définissant une ressource REST permettant de supprimer un
     * utilisateur
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        //On retrouve l'entité à partir de son identifiant
        User user = userService.findOne(id);
        // Suppression de l'entité
        userService.delete(user);
        // Tout se passe bien
        return ResponseEntity.ok(null);
    }

}
