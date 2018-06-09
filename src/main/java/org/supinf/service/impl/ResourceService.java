package org.supinf.service.impl;

import org.supinf.dao.ResourceRepository;
import org.supinf.entities.Resource;
import org.supinf.service.IResourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supinf.config.JwtWebSecurityConfigurerAdapter;
import org.supinf.service.IFolderResourceService;

/**
 *
 * @author Edem Amegbeto
 */
@Service
@Transactional
public class ResourceService implements IResourceService {

    /**
     * Injection instance Repository
     */
    @Autowired
    ResourceRepository resourceRepository;

    /**
     * injection instance IFolderResourceService
     */
    @Autowired
    private IFolderResourceService folderResourceService;

    /**
     * @see IResourceService#save(org.supinf.entities.Resource)
     */
    @Override
    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    /**
     * @see IResourceService#findOne(java.lang.Long)
     */
    @Override
    public Resource findOne(Long id) {
        return resourceRepository.findById(id).get();
    }

    /**
     * @see IResourceService#update(org.supinf.entities.Resource)
     */
    @Override
    public Resource update(Resource resource) {
        return resourceRepository.saveAndFlush(resource);
    }

    /**
     * @see IResourceService#findAll()
     */
    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    /**
     * @see IResourceService#delete(org.supinf.entities.Resource)
     */
    @Override
    public void delete(Resource resource) {
        resourceRepository.delete(resource);
    }

    /**
     * @see IResourceService#exists(java.lang.Long, java.lang.Long,
     * java.lang.String)
     */
    @Override
    public Boolean exists(Long ownerId, Long parentFolderId, String name) {
        if (parentFolderId == null) {
            parentFolderId = folderResourceService.findUserDefaultFolder(ownerId).getId();
        }
        // une ressource existe déja si cette liste n'est pas vise
        return !resourceRepository.findByUserIdAndResourceIdAndName(ownerId, parentFolderId, name).isEmpty();
    }
}
