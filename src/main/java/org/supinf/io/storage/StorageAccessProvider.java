package org.supinf.io.storage;

import org.supinf.entities.FileResource;
import org.supinf.entities.FolderResource;
import org.supinf.entities.Resource;
import org.supinf.entities.User;

/**
 * CEtte interface fournit les fonctionnalités permettant d'interagir avec un
 * system de stockage
 *
 * @author BLU Kwensy Eli
 */
public interface StorageAccessProvider {

    /**
     *
     * @param resource
     */
    public void createResource(Resource resource);

    /**
     *
     * @param fileResource
     */
    public void createFile(FileResource fileResource, Object originalFile) throws Exception;

    /**
     *
     * @param folderResource
     */
    public void createFolder(FolderResource folderResource);

    /**
     *
     * @param source
     * @param destination
     */
    public void moveResource(Resource source, FolderResource destination);

    /**
     *
     * @param resource
     */
    public void deleteResource(Resource resource);

    /**
     *
     * @param resource
     * @param name
     */
    public void renameResource(Resource resource, String name);

    /**
     * Cette méthode permet de creer le répertoire racine
     */
    public void createRootFolder();
    
    /**
     * initialiser l'espace de stockage de l'utilisateur
     */
    public void initUserStorageSpace(User user);

}
