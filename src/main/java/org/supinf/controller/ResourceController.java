package org.supinf.controller;

import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supinf.entities.FileResource;
import org.supinf.entities.FolderResource;
import org.supinf.entities.Resource;
import org.supinf.entities.User;
import org.supinf.entities.projection.UserWithoutPassword;
import org.supinf.io.storage.AbstractStorageAccessProvider;
import org.supinf.service.IAuthenticationService;
import org.supinf.service.IFileResourceService;
import org.supinf.service.IFolderResourceService;
import org.supinf.service.IResourceService;
import org.supinf.service.IUserService;
import org.supinf.webapi.FolderResourceRequest;
import org.supinf.webapi.GetResourceResponse;
import org.supinf.webapi.GetResourceResponseExtended;
import org.supinf.webapi.RenameResourceRequest;
import org.supinf.webapi.ResourceResponse;

/**
 *
 * @author BLU Kwensy Eli
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

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
        LOGGER.info("Connected user ***************************************** " + connectedUser);

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
        LOGGER.info("Destination file ***************************************** " + persistedFileResource);

        String message = "Fichier téléchargé avec succès ...";
        HttpStatus status = HttpStatus.OK;
        try {
            storageAccess.createFile(persistedFileResource, file);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
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
     * Renommer un fichier
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "Renommer un fichier en conservant son extension")
    @PostMapping(value = "/files/rename", consumes = "application/json")
    public ResponseEntity<ResourceResponse> renameFile(@RequestBody RenameResourceRequest renameResourceRequest) {

        // on récupère un dossier depuis les informations envoyées
        FileResource file = fileResourceService.findOne(renameResourceRequest.getId());
        String fileName = file.getName();
        String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
        System.out.println("extension = " + extension);
        String newName = renameResourceRequest.getName().concat(extension);
        // si la ressource existe déjà
        if (resourceService.isDuplicated(file.getUser().getId(), file.getResource().getId(), newName)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse("Un fichier de même nom existe déjà", null));
        }
        // clone
        FileResource oldFile = new FileResource(file.getName(), null, file.getUser(), file.getSize());
        oldFile.setId(file.getId());
        oldFile.setResource(file.getResource());
        // modèle màj
        file.setName(newName);
        //créer le dossier dans la base de données
        FileResource persistedFile = fileResourceService.update(file);
        //on crée le dossier sur le système de fichier
        storageAccess.renameResource(oldFile, newName);
        // Création 
        return ResponseEntity.ok(new ResourceResponse("Fichier renommé avec succès", persistedFile.getId()));
    }

    /**
     * Renommer un fichier
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Recupérer les informations concernant une ressource")
    @GetMapping
    public ResponseEntity<GetResourceResponse> getResource(@RequestParam(required = false) Long id) {

        // id de l'utilisateur connecté
        Long connectedUserId = authenticationService.getAuthenticatedUser().getId();

        Resource resource;
        //identifiant de la ressource null
        if (id == null) {
            resource = folderResourceService.findUserDefaultFolder(connectedUserId);
        } else {
            resource = resourceService.findOne(id);
        }
        GetResourceResponseExtended getResourceResponse = GetResourceResponseExtended.clone(fromResource(resource));

        // dossier parent
        Resource parent = resource.getResource();
        getResourceResponse.setParentId((parent != null) ? parent.getId() : null);

        if (resource instanceof FileResource) {
            // Fichier 
            getResourceResponse.setResources(null);
        } else {
            // Dossier 
            List<Resource> childResources = (id == null) ? resourceService.findByUserIdAndResourceIsNotNull(connectedUserId) : resourceService.findByUserIdAndResourceId(connectedUserId, id);
            getResourceResponse.setResources(childResources.parallelStream().map(res -> {
                return fromResource(res);
            }).collect(Collectors.toList()));
        }
        //
        return ResponseEntity.ok(getResourceResponse);
    }

    /**
     *
     * @param resource
     * @return
     */
    private GetResourceResponse fromResource(Resource resource) {

        GetResourceResponse resourceWebApiRepresentation = new GetResourceResponse();
        //identifiant
        resourceWebApiRepresentation.setId(resource.getId());
        //nom
        resourceWebApiRepresentation.setName(resource.getName());

        // taille de la ressource
        resourceWebApiRepresentation.setSize(resource instanceof FileResource ? ((FileResource) resource).getSize() : null);
        // type de la ressource
        resourceWebApiRepresentation.setType(resource instanceof FileResource ? "FILER" : "FOLDR");

        return resourceWebApiRepresentation;
    }

    @GetMapping("/files/download/{id}")
    public ResponseEntity<Object> downloadFile(@PathVariable Long id, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileResourceService.findOne(id);

        // si le fichier n'est pas un PDF
        String fileName = resource.getName();
        
        if (!fileName.endsWith(".pdf")) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse("ce type de fichier n'est pas encore pris en charge", null));
        }
        String fileAbsolutePath = storageAccess.buildResourceAbsolutePath(resource);
        
        File file = new File(fileAbsolutePath);

        // Try to determine file's content type
        String contentType = null;

        contentType = request.getServletContext().getMimeType(file.getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/pdf";
        }

        try {

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getName() + "\"")
                    .body(new InputStreamResource(new FileInputStream(file)));
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getName() + "\"")
//                .body(file);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(ResourceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResourceResponse("Impossible de télécharger un dossier", null));
    }
}
