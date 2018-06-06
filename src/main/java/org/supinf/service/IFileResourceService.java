package org.supinf.service;

import org.supinf.entities.FileResource;
import java.util.List;
import org.supinf.dao.FileResourceRepository;

/**
 *
 * @author Edem Amegbeto
 */
public interface IFileResourceService {

    /**
     * Créer un fichier
     *
     * @param fileResource
     * @return l'entité créée
     */
    public FileResource save(FileResource fileResource);

    /**
     * Rechercher un fichier grâce à son identifiant
     *
     * @param id
     * @return l'entité recherchée
     */
    public FileResource findOne(Long id);

    /**
     * Mettre à jour un fichier
     *
     * @param fileResource
     * @return L'entité avec ses attributs màj
     */
    public FileResource update(FileResource fileResource);

    /**
     * Rechercher tous les fichier
     *
     * @return Toutes les instances de l'entité
     */
    public List<FileResource> findAll();

    /**
     * Supprimer un fichier
     *
     * @param fileResource
     */
    public void delete(FileResource fileResource);

    
}
