package org.supinf.webapi;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 *
 * @author BLU Kwensy Eli
 */
public class GetResourceResponseExtended extends GetResourceResponse {

    public static GetResourceResponseExtended clone(GetResourceResponse original) {
        GetResourceResponseExtended clone = new GetResourceResponseExtended();
        clone.setId(original.id);
        clone.setName(original.name);
        clone.setSize(original.size);
        clone.setType(original.type);

        return clone;
    }
    /**
     * L'identifiant de la ressource parent
     */
    @ApiModelProperty(value = "Identifiant du dossier parent")
    protected Long parentId;

    /**
     * liste des ressources enfants (contenu d'un dossier)
     */
    protected List<GetResourceResponse> resources;

    /**
     *
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     *
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     *
     * @return
     */
    public List<GetResourceResponse> getResources() {
        return resources;
    }

    /**
     *
     * @param resources
     */
    public void setResources(List<GetResourceResponse> resources) {
        this.resources = resources;
    }
}
