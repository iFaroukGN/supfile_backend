package org.supinf.io.storage;

import org.supinf.entities.FileResource;
import org.supinf.entities.FolderResource;
import org.supinf.entities.Resource;

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
    public void createFile(FileResource fileResource);

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

}
