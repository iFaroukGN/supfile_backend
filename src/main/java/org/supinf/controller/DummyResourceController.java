package org.supinf.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supinf.entities.FolderResource;
import org.supinf.entities.User;
import org.supinf.entities.projection.UserWithoutPassword;
import org.supinf.io.storage.AbstractStorageAccessProvider;
import org.supinf.service.IAuthenticationService;
import org.supinf.service.IFolderResourceService;
import org.supinf.service.IUserService;
import org.supinf.webapi.FolderResourceRequest;
import org.supinf.webapi.FolderResourceResponse;

/**
 *
 * @author BLU Kwensy Eli
 */
@RestController
@RequestMapping("/resource")
public class DummyResourceController {

    /**
     * injection instance StorageAccessProvider
     */
    @Autowired
    @Qualifier("localFileSystemIoHandler")
    AbstractStorageAccessProvider storageAccess;

    /**
     * injection instance IFolderResourceService
     */
    @Autowired
    private IFolderResourceService folderResourceService;

    /**
     * injection instance IAuthenticationService
     */
    @Autowired
    @Qualifier("defaultAuthenticationService")
    private IAuthenticationService authenticationService;

    /**
     * Injection instance IUserService
     */
    @Autowired
    private IUserService userService;

    /**
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file) {

        String message = "File successfully uploaded ...";
        HttpStatus status = HttpStatus.OK;
        try {
            storageAccess.createFile(null, file);
        } catch (Exception ex) {
            Logger.getLogger(DummyResourceController.class.getName()).log(Level.SEVERE, null, ex);
            message = " error while uploading file ...";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(message, status);
    }

    /**
     * Créer un dossier
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/folders", consumes = "application/json")
    public ResponseEntity<FolderResourceResponse> createFolder(@RequestBody FolderResourceRequest folderResourceRequest) {

        // id de l'utilisateur connecté
        Long connectedUserId = authenticationService.getAuthenticatedUser().getId();

        // l'id de la ressource parent du dossier à créer
        Long parentFolderId = folderResourceRequest.getParentId();

        // On récupère l'utilisateur connecté
        User connectedUser = userService.findOne(connectedUserId);

        // on crée un dossier depuis les informations envoyées
        FolderResource folder = new FolderResource(folderResourceRequest.getName(), null, connectedUser);

        // On récupère le répertoire par défaut de l'utilisateur
        FolderResource userRootFolder = folderResourceService.findUserDefaultFolder(connectedUserId);

        // si le dossier est créé directement dans l'espace de stockage de l'utilisateur
        if (parentFolderId == null || parentFolderId <= 0) {
            folder.setResource(userRootFolder);
            System.out.println("folder has no parent");
        } else {
            folder.setResource(folderResourceService.findOne(parentFolderId));
            System.out.println("folder has parent");
        }

        //créer le dossier dans la base de données
        FolderResource persistedFolder = folderResourceService.save(folder);

        //on crée le dossier sur le système de fichier
        storageAccess.createFolder(persistedFolder);
        // Création 
        return ResponseEntity.ok(null);
    }

}
