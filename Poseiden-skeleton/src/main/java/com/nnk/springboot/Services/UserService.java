package com.nnk.springboot.Services;

import com.nnk.springboot.utils.UserDto;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a list of all users from the database
     * @return a list of Users
     */
    @Transactional(readOnly=true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    /**
     * Saves a user in database
     * @param user a user
     * @return the user in database
     */
    public User save(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieve a user from the database by its id
     * @param id the user id
     * @return Optional of User
     */
    @Transactional(readOnly=true)
    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    /**
     * Retrieve a user from the database by its username
     * @param username the username
     * @return Optional of User
     */
    @Transactional(readOnly=true)
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Updates a user
     * @param user user informations to be updated
     * @param id the user id in database
     * @return the user in database
     */
    public User update(UserDto user, int id){
        User inDb = userRepository.getOne(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        inDb.setFullname(user.getFullname());
        inDb.setPassword(encoder.encode(user.getPassword()));
        inDb.setRole(user.getRole());
        return userRepository.save(inDb);
    }

    /**
     * Deletes a user from the database
     * @param id the user id
     */
    public void delete(int id){
        Optional<User> opt = this.findById(id);
        if(opt.isPresent()){
            userRepository.delete(opt.get());
        }
    }
}
