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
}
