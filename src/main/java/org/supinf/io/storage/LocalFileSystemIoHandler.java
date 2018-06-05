package org.supinf.io.storage;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
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
public class LocalFileSystemIoHandler extends AbstractStorageAccessProvider {

    @Override
    public void createResource(Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createFile(FileResource fileResource, Object originalFile) throws IOException {
        MultipartFile file = (MultipartFile) originalFile;
        File destination = new File(getStorageRootPath() + file.getOriginalFilename());
        file.transferTo(destination);
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

    @Override
    public void createRootFolder() {
        File rootFolder = new File(getStorageRootPath());
        if (!rootFolder.exists()) {
            rootFolder.mkdir();
        }
    }
}
