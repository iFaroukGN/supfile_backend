package org.supinf.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FILER")
public class FileResource extends Resource {
	private long size;

	//Getters and Setters
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	//Constructeur sans parametre
	public FileResource() {
		super();
	}
	
	//Constructeur avec parametre
	public FileResource(String name, String path, User user, long size) {
		super(name, path, user);
		this.size = size;
	}
	
	
	
	
	
	
	

}
