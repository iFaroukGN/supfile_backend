package org.supinf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.supinf.entities.FileResource;

public interface FileResourceRepository extends JpaRepository<FileResource, Long> {

}
