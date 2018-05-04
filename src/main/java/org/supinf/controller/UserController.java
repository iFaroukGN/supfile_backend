package org.supinf.controller;

import org.supinf.entities.User;
import org.supinf.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe controlleur
 *
 * @author BLU Kwensy Eli
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Injection d'une instance de IUserService
     */
    @Autowired
    IUserService userService;

    /**
     * Méthode définissant une ressource REST permettant de créer un utilisateur
     *
     * @param user
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    /**
     * Méthode définissant une ressource REST permettant de retrouver un utilisateur
     * grâce à son identifiant
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOne(id));
    }

    /**
     * Méthode définissant une ressource REST permettant de modifier un utilisateur
     *
     * @param user
     * @return
     */
    @PutMapping("/")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    /**
     * Méthode définissant une ressource REST permettant de retrouver la liste de tous les utilisateurs
     *
     * @return La liste des utilisateurs
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Méthode définissant une ressource REST permettant de supprimer un utilisateur
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
