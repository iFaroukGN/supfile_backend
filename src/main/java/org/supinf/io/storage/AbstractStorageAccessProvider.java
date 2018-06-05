package org.supinf.io.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 * Classe abstraite pour interagir avec le système de stockage
 *
 * @author BLU Kwensy Eli
 *
 */
public abstract class AbstractStorageAccessProvider implements StorageAccessProvider {

//    @Autowired
//    private Environment env;
    //Le répertoire de racine du sysème de stockage
    @Value("${storage.root}")
    private String storageRootPath;

    // Getter 
    public String getStorageRootPath() {
        return storageRootPath+"/";
    }

}
