package com.nnk.springboot.Services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> opt = this.userRepository.findByUsername(userName);
        if (opt.isEmpty()){
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        User user = opt.get();

        // [USER, ADMIN,..]
        String role = user.getRole();

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (role != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            grantList.add(authority);
        }

        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(), //
                user.getPassword(), grantList);

        return userDetails;
    }
}
