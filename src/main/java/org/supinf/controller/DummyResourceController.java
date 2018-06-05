package org.supinf.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supinf.entities.FolderResource;
import org.supinf.io.storage.AbstractStorageAccessProvider;
import org.supinf.io.storage.StorageAccessProvider;

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

}
