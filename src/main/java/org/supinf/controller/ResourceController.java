package org.supinf.controller;

import io.swagger.annotations.ApiOperation;
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
import org.supinf.entities.FileResource;
import org.supinf.entities.FolderResource;
import org.supinf.entities.User;
import org.supinf.entities.projection.UserWithoutPassword;
import org.supinf.io.storage.AbstractStorageAccessProvider;
import org.supinf.service.IAuthenticationService;
import org.supinf.service.IFileResourceService;
import org.supinf.service.IFolderResourceService;
import org.supinf.service.IResourceService;
import org.supinf.service.IUserService;
import org.supinf.webapi.FolderResourceRequest;
import org.supinf.webapi.RenameResourceRequest;
import org.supinf.webapi.ResourceResponse;

/**
 *
 * @author BLU Kwensy Eli
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    /**
     * injection instance StorageAccessProvider
     */
    @Autowired
    @Qualifier("localFileSystemIoHandler")
    AbstractStorageAccessProvider storageAccess;

    /**
     * injection instance IResourceService
     */
    @Autowired
    private IResourceService resourceService;

    /**
     * injection instance IFolderResourceService
     */
    @Autowired
    private IFolderResourceService folderResourceService;

    /**
     * injection instance IFileResourceService
     */
    @Autowired
    private IFileResourceService fileResourceService;

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
    @ApiOperation(value = "Télécharger un fichier dans un dossier")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file, @RequestParam(required = false) Long parentFolderId) {
        // id de l'utilisateur connecté
        Long connectedUserId = authenticationService.getAuthenticatedUser().getId();

        // si la ressource existe déjà
        if (resourceService.isDuplicated(connectedUserId, parentFolderId, file.getOriginalFilename())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse("Un fichier de même nom existe déjà", null));
        }

        // On récupère l'utilisateur connecté
        User connectedUser = userService.findOne(connectedUserId);

        // On récupère le répertoire par défaut de l'utilisateur
        FolderResource userRootFolder = folderResourceService.findUserDefaultFolder(connectedUserId);

        FileResource fileResource = new FileResource(file.getOriginalFilename(), null, connectedUser, file.getSize());

        // si le dossier est créé directement dans l'espace de stockage de l'utilisateur
        if (parentFolderId == null || parentFolderId <= 0) {
            fileResource.setResource(userRootFolder);
        } else {
            fileResource.setResource(folderResourceService.findOne(parentFolderId));
        }
        // on sauvegarde le fichier dns la base de données
        FileResource persistedFileResource = fileResourceService.save(fileResource);

        String message = "Fichier téléchargé avec succès ...";
        HttpStatus status = HttpStatus.OK;
        try {
            storageAccess.createFile(persistedFileResource, file);
        } catch (Exception ex) {
            Logger.getLogger(ResourceController.class.getName()).log(Level.SEVERE, null, ex);
            message = "Erreur lors du téléchargement  ...";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ResourceResponse response = new ResourceResponse(message, persistedFileResource.getId());
        return new ResponseEntity<>(response, status);
    }

    /**
     * Créer un dossier
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "Créer un dossier")
    @PostMapping(value = "/folders", consumes = "application/json")
    public ResponseEntity<Object> createFolder(@RequestBody FolderResourceRequest folderResourceRequest) {

        //le nom du dossier
        String resourceName = folderResourceRequest.getName();

        // l'id de la ressource parent du dossier à créer
        Long parentFolderId = folderResourceRequest.getParentId();

        // id de l'utilisateur connecté
        Long connectedUserId = authenticationService.getAuthenticatedUser().getId();

        // si la ressource existe déjà
        if (resourceService.isDuplicated(connectedUserId, parentFolderId, resourceName)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse("Un dossier de même nom existe déjà", null));
        }

        // On récupère l'utilisateur connecté
        User connectedUser = userService.findOne(connectedUserId);

        // on crée un dossier depuis les informations envoyées
        FolderResource folder = new FolderResource(resourceName, null, connectedUser);

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
        return ResponseEntity.ok(new ResourceResponse("Dossier créé avec succès", persistedFolder.getId()));
    }

    /**
     * Créer un dossier
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "Renommer un dossier")
    @PostMapping(value = "/folders/rename", consumes = "application/json")
    public ResponseEntity<ResourceResponse> renameFolder(@RequestBody RenameResourceRequest renameResourceRequest) {

        // TODO contrôler  que le dossier qui est en train d'être renommé n'est pas celui par défaut de l'utilisateur
        // on récupère un dossier depuis les informations envoyées
        FolderResource folder = folderResourceService.findOne(renameResourceRequest.getId());
        
        String newName = renameResourceRequest.getName();
         // si la ressource existe déjà
        if (resourceService.isDuplicated(folder.getUser().getId(), folder.getResource().getId(), newName)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse("Un dossier de même nom existe déjà", null));
        }

        // clone
        FolderResource oldFolder = new FolderResource(folder.getName(), null, folder.getUser());
        oldFolder.setId(folder.getId());
        oldFolder.setResource(folder.getResource());

        // modèle màj
        folder.setName(renameResourceRequest.getName());

        //créer le dossier dans la base de données
        FolderResource persistedFolder = folderResourceService.update(folder);

        //on crée le dossier sur le système de fichier
        storageAccess.renameResource(oldFolder, renameResourceRequest.getName());
        // Création 
        return ResponseEntity.ok(new ResourceResponse("Dossier renommé avec succès", persistedFolder.getId()));
    }

    /**
     * Créer un dossier
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "Renommer un fichier")
    @PostMapping(value = "/files/rename", consumes = "application/json")
    public ResponseEntity<ResourceResponse> renameFile(@RequestBody RenameResourceRequest renameResourceRequest) {

        // id de l'utilisateur connecté
        Long connectedUserId = authenticationService.getAuthenticatedUser().getId();

        // On récupère l'utilisateur connecté
        User connectedUser = userService.findOne(connectedUserId);

        // on crée un dossier depuis les informations envoyées
        FileResource file = fileResourceService.findOne(renameResourceRequest.getId());

        file.setName(renameResourceRequest.getName());

        //créer le dossier dans la base de données
        FileResource persistedFolder = fileResourceService.update(file);

        //on crée le dossier sur le système de fichier
        storageAccess.renameResource(file, renameResourceRequest.getName());
        // Création 
        return ResponseEntity.ok(new ResourceResponse("Fichier renommé avec succès", persistedFolder.getId()));
    }
}
