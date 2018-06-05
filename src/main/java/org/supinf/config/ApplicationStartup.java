package org.supinf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.supinf.io.storage.StorageAccessProvider;


@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    
    /**
     * injection instance StorageAccessProvider
     */
    @Autowired
    @Qualifier("localFileSystemIoHandler")
    StorageAccessProvider storageAccess;
    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        storageAccess.createRootFolder();
    }

} // class
