package org.supinf.webapi;

import io.swagger.annotations.ApiModelProperty;

/**
 * Classe encapsulant les informations sur un dossier en réponse à une requête
 * HTTP
 *
 * @author BLU Kwensy Eli
 */
public class FolderResourceRequest {

    /**
     * L'identifiant de l'entité
     */
    protected Long id;

    /**
     * Le nom de la resource
     */
    @ApiModelProperty(value = "Nom du dossier")
    protected String name;

    /**
     * L'identifiant de la ressource parent
     */
    @ApiModelProperty(value = "Identifiant du dossier parent")
    protected Long parentId;

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 
     * @param parentId si null, le répertoire parent sera l'espace par défaut de l'utilisateur
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
