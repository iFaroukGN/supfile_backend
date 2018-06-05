package org.supinf.service.impl;

import org.supinf.dao.FolderResourceRepository;
import org.supinf.entities.FolderResource;
import org.supinf.service.IFolderResourceService;
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
public class FolderResourceService implements IFolderResourceService {

    /**
     * Injection instance Repository
     */
    @Autowired
    FolderResourceRepository folderResourceRepository;

    /**
     * @see IFolderResourceService#save(org.supinf.entities.FolderResource)
     */
    @Override
    public FolderResource save(FolderResource folderResource) {
        return folderResourceRepository.save(folderResource);
    }

    /**
     * @see IFolderResourceService#findOne(java.lang.Long)
     */
    @Override
    public FolderResource findOne(Long id) {
        return folderResourceRepository.findById(id).get();
    }

    /**
     * @see IFolderResourceService#update(org.supinf.entities.FolderResource)
     */
    @Override
    public FolderResource update(FolderResource folderResource) {
        return folderResourceRepository.saveAndFlush(folderResource);
    }

    /**
     * @see IFolderResourceService#findAll()
     */
    @Override
    public List<FolderResource> findAll() {
        return folderResourceRepository.findAll();
    }

    /**
     * @see IFolderResourceService#delete(org.supinf.entities.FolderResource)
     */
    @Override
    public void delete(FolderResource folderResource) {
        folderResourceRepository.delete(folderResource);
    }
    
    
    

}
