package org.supinf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.supinf.entities.FolderResource;

public interface FolderResourceRepository extends JpaRepository<FolderResource, Long>{

    
    /**
     * Récupérer le dossier qui n'a pas de parent pour l'utilisateur dont
     * l'identifiant est passé en paramètre
     *
     * @param ownerId
     * @return
     */
    public FolderResource findOneByUserIdAndResourceIsNull(Long ownerId);
}
