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

}
