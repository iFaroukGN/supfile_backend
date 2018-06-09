package org.supinf.entities;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.supinf.entities.User;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type_Resource",
        discriminatorType = DiscriminatorType.STRING, length = 5)
//il ne peut pas y avoir deux fichiers ou dossiers de même nom dans le même dossier pour le même utilisateur
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"NAME","PARENT","OWNER"}))
public abstract class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "null", dataType = "java.lang.Integer", required = false)
    protected Long id;
    @Column(name = "NAME")
    protected String name;

    @ApiModelProperty(hidden = true, value = "variable0", dataType = "java.lang.String", required = false)
    protected String path;

    @ManyToOne
    @JoinColumn(name = "PARENT")
    @ApiModelProperty(dataType = "org.supinf.webapi.ResourceResponse", required = false)
    protected Resource resource;

    @ManyToOne
    @JoinColumn(name = "OWNER")
    @ApiModelProperty(dataType = "org.supinf.webapi.UserResponse", required = false)
    protected User user;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    // Constructeur sans parametre
    public Resource() {
        super();
        // TODO Auto-generated constructor stub
    }

    //Constructeur avec parametre 
    public Resource(String name, String path, User user, Resource resource) {
        super();
        this.name = name;
        this.path = path;
        this.user = user;
        this.resource = resource;
    }

    //Constructeur avec parametre 
    public Resource(String name, String path, User user) {
        super();
        this.name = name;
        this.path = path;
        this.user = user;
    }

}
