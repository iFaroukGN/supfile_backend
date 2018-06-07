package org.supinf.webapi;

import io.swagger.annotations.ApiModelProperty;

/**
 * Classe encapsulant les informations sur pour modifier une ressource
 * HTTP
 *
 * @author BLU Kwensy Eli
 */
public class RenameResourceRequest {

    /**
     * L'identifiant de la ressource
     */
    @ApiModelProperty(value = "identifiant de la ressource")
    protected Long id;

    /**
     * Le nom de la resource
     */
    @ApiModelProperty(value = "Nom de la ressource")
    protected String name;


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
}
