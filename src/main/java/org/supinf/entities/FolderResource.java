package org.supinf.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOLDR")
public class FolderResource extends Resource {

    //Getters and Setters
    //Costructeur sans param
    public FolderResource() {

    }

    //Constructeur avec param
    public FolderResource(String name, String path, User user) {
        super(name, path, user);

    }

}
