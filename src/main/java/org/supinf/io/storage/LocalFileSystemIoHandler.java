package org.supinf.io.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.supinf.controller.ResourceController;
import org.supinf.entities.FileResource;
import org.supinf.entities.FolderResource;
import org.supinf.entities.Resource;
import org.supinf.entities.User;
import org.supinf.service.impl.FolderResourceService;

/**
 * Classe pour interagir avec le système de fichiers local
 *
 * @author BLU Kwensy Eli
 *
 */
@Component
public class LocalFileSystemIoHandler extends AbstractStorageAccessProvider {

    public static final Logger LOGGER = LoggerFactory.getLogger(LocalFileSystemIoHandler.class);
    /**
     * Constante
     */
    public static final String PATH_SEPARATOR = "/";

    /**
     * @see StorageAccessProvider#createResource(org.supinf.entities.Resource)
     */
    @Override
    public void createResource(Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createFile(FileResource fileResource, Object originalFile) throws IOException {
        MultipartFile file = (MultipartFile) originalFile;
        String absoluteFilePath = buildResourceAbsolutePath(fileResource);
        LOGGER.info("Destination path ***************************************** " + absoluteFilePath);
        File destination = new File(absoluteFilePath);
        file.transferTo(destination);
    }

    @Override
    public void createFolder(FolderResource folderResource) {
        String resourceAbsolutePath = buildResourceAbsolutePath(folderResource);
        File file = new File(resourceAbsolutePath);
        file.mkdir();
    }

    @Override
    public void moveResource(Resource source, FolderResource destination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteResource(Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void renameResource(Resource oldResource, String name) {

        // construction du chemin absolu vers la ressource
        String absoluteResourcePath = buildResourceAbsolutePath(oldResource);
        // instance File représentant la ressource
        File oldFile = new File(absoluteResourcePath);

        // chemin vers le répertoire parent   |  C:/path/to/resource   ==>    C:/path/to/
        int pathSeparatorLastIndex = absoluteResourcePath.lastIndexOf(PATH_SEPARATOR);
        String absoluteResourcePathParent = absoluteResourcePath.substring(0, pathSeparatorLastIndex + 1);

        // instance File représentant la ressource avec le nouveu nom
        File newFile = new File(absoluteResourcePathParent, name);
        oldFile.renameTo(newFile);

    }

    @Override
    public void createRootFolder() {
        File rootFolder = new File(getStorageRootPath());
        if (!rootFolder.exists()) {
            rootFolder.mkdir();
        }
    }

    @Override
    public void initUserStorageSpace(User user) {
        File userStorageSpace = new File(getStorageRootPath(), userStorageSpaceResourceName(user));
        if (!userStorageSpace.exists()) {
            userStorageSpace.mkdir();
            persistUserStorageSpaceResource(user);
        }
    }

    private String userStorageSpaceResourceName(User user) {
        return String.valueOf(user.getId());
    }

    private void persistUserStorageSpaceResource(User user) {
        FolderResource userStorageResource = new FolderResource(userStorageSpaceResourceName(user), null, user);
        folderResourceService.save(userStorageResource);
    }

    @Override
    public String buildResourceAbsolutePath(Resource resource) {

        // si la resource parent de la ressource courante est null c'est qu'on est à la racine
        FolderResource parent = (FolderResource) resource.getResource();

        //
        String resourceName = resource.getName();
        if (parent == null) {
            return getStorageRootPath() + resourceName;
        }
        // on récupère le parent de la base de données pour avoir son ascendence
        FolderResource persistedParent = folderResourceService.findOne(parent.getId());

        return String.join("", buildResourceAbsolutePath(persistedParent), PATH_SEPARATOR, resourceName);
    }
}
