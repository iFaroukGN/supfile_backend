
package org.supinf.dao;

import org.supinf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BLU Kwensy Eli
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
}
