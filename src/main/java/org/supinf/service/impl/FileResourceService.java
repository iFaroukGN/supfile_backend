package org.supinf.service.impl;

import org.supinf.dao.FileResourceRepository;
import org.supinf.entities.FileResource;
import org.supinf.service.IFileResourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supinf.config.JwtWebSecurityConfigurerAdapter;

/**
 *
 * @author Edem 
 */
@Service
@Transactional
public class FileResourceService implements IFileResourceService {

    /**
     * Injection instance Repository
     */
    @Autowired
    FileResourceRepository fileResourceRepository;

    /**
     * @see IFileResourceService#save(org.supinf.entities.FileResource)
     */
    @Override
    public FileResource save(FileResource fileResource) {
        return fileResourceRepository.save(fileResource);
    }

    /**
     * @see IFileResourceService#findOne(java.lang.Long)
     */
    @Override
    public FileResource findOne(Long id) {
        return fileResourceRepository.findById(id).get();
    }

    /**
     * @see IFileResourceService#update(org.supinf.entities.FileResource)
     */
    @Override
    public FileResource update(FileResource fileResource) {
        return fileResourceRepository.saveAndFlush(fileResource);
    }

    /**
     * @see IFileResourceService#findAll()
     */
    @Override
    public List<FileResource> findAll() {
        return fileResourceRepository.findAll();
    }

    /**
     * @see IFileResourceService#delete(org.supinf.entities.FileResource)
     */
    @Override
    public void delete(FileResource fileResource) {
        fileResourceRepository.delete(fileResource);
    }
    
    
    

}
