package org.supinf.io.storage;

import org.springframework.stereotype.Component;
import org.supinf.entities.FileResource;
import org.supinf.entities.FolderResource;
import org.supinf.entities.Resource;

/**
 * Classe pour interagir avec le système de fichiers local
 *
 * @author BLU Kwensy Eli
 * 
 */
@Component
public class LocalFileSystemIoHandler implements StorageAccessProvider {

    @Override
    public void createResource(Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createFile(FileResource fileResource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createFolder(FolderResource folderResource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void renameResource(Resource resource, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
