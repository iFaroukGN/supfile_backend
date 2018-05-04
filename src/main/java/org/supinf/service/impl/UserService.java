package org.supinf.service.impl;

import org.supinf.dao.UserRepository;
import org.supinf.entities.User;
import org.supinf.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BLU Kwensy Eli
 */
@Service
@Transactional
public class UserService implements IUserService {

    /**
     * Injection instance Repository
     */
    @Autowired
    UserRepository userRepository;

    /**
     * @see IUserService#save(org.supinf.entities.User)
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * @see IUserService#findOne(java.lang.Long)
     */
    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * @see IUserService#update(org.supinf.entities.User)
     */
    @Override
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    /**
     * @see IUserService#findAll()
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * @see IUserService#delete(org.supinf.entities.User)
     */
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

}
