package org.supinf.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.supinf.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long>{
	
    /**
     * Recuperer les ressources qui ont un meme nom, un meme parent et le eme proprietaire
     *
     * @param resource
     */
    public List<Resource> findByUserIdAndResourceIdAndName(Long ownerId, Long ParentFolderId, String name);
    
    /**
     * Recuperer les ressources qui ont un meme parent et créées par le meme utilisateur
     *
     * @param resource
     */
    public List<Resource> findByUserIdAndResourceId(Long ownerId, Long ParentFolderId);
    
    /**
     * Recuperer les ressources qui ont été créées par le même utilisateur
     *
     * @param resource
     */
    public List<Resource> findByUserIdAndResourceIsNotNull(Long ownerId);
    
}
