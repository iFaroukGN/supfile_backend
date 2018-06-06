package org.supinf.service;

import org.supinf.entities.FolderResource;
import java.util.List;
import org.supinf.dao.FolderResourceRepository;

/**
 *
 * @author Edem Amegbeto
 */
public interface IFolderResourceService {

    /**
     * Créer un dossier
     *
     * @param folderResource
     * @return l'entité créée
     */
    public FolderResource save(FolderResource folderResource);

    /**
     * Rechercher un dossier grâce à son identifiant
     *
     * @param id
     * @return l'entité recherchée
     */
    public FolderResource findOne(Long id);

    /**
     * Mettre à jour un dossier
     *
     * @param folderResource
     * @return L'entité avec ses attributs màj
     */
    public FolderResource update(FolderResource folderResource);

    /**
     * Rechercher tous les dossiers
     *
     * @return Toutes les instances de l'entité
     */
    public List<FolderResource> findAll();

    /**
     * Supprimer un dossier
     *
     * @param folderResource
     */
    public void delete(FolderResource folderResource);

    /**
     * Récupérer le dossier par défaut de l'utilisateur dont
     * l'identifiant est passé en paramètre
     *
     * @param ownerId
     * @return
     */
    public FolderResource findUserDefaultFolder(Long ownerId);

}
