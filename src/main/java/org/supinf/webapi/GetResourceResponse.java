package org.supinf.webapi;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * Classe encapsulant les informations renvoyées en réponse à une requête HTTP
 * Get sur une ressource
 *
 * @author BLU Kwensy Eli
 */
public class GetResourceResponse {

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
     * la taille de la ressource
     */
    @ApiModelProperty(value = "la taille de la ressource")
    protected Long size;

    /**
     * le type de la ressource
     */
    @ApiModelProperty(value = "le type de la ressource")
    protected String type;

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
    public Long getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

}
