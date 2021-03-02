package com.nnk.springboot.Services;

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

    @Transactional(readOnly=true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User add(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly=true)
    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User update(User user, int id){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        return userRepository.save(user);
    }

    public void delete(int id){
        Optional<User> opt = this.findById(id);
        if(opt.isPresent()){
            userRepository.delete(opt.get());
        }
    }
}
