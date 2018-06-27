package org.supinf.service;

import org.supinf.entities.Resource;
import java.util.List;
import org.supinf.dao.ResourceRepository;
import org.supinf.entities.projection.ResourceWithoutPassword;

/**
 *
 * @author Edem Amegbeto
 */
public interface IResourceService {

    /**
     * Créer une resource
     *
     * @param resource
     * @return l'entité créée
     */
    public Resource save(Resource resource);

    /**
     * Rechercher un resource grâce à son identifiant
     *
     * @param id
     * @return l'entité recherchée
     */
    public Resource findOne(Long id);

    /**
     * Mettre à jour une resource
     *
     * @param resource
     * @return L'entité avec ses attributs màj
     */
    public Resource update(Resource resource);

    /**
     * Rechercher toutes les resources
     *
     * @return Toutes les instances de l'entité
     */
    public List<Resource> findAll();

    /**
     * Supprimer une resource
     *
     * @param resource
     */
    public void delete(Resource resource);
    
    /**
     * Vérifier si une resource existe déjà
     *
     * @param resource
     */
    public Boolean isDuplicated(Resource resource);
    
    /**
     * Vérifie si une ressource existe en prenant en compte les critères en paramètre
     * 
     * @param ownerId
     * @param parentFolderId
     * @param name
     * @return 
     */
    public Boolean isDuplicated(Long ownerId, Long parentFolderId, String name);
    
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
