package com.nurul.blog.service;


import com.nurul.blog.entity.User;
import com.nurul.blog.entity.UserPrinciple;
import com.nurul.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getALlUsers() {
        return userRepository.findAll();
    }

    public void storeUser(User user) {
        userRepository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrinciple(user);
    }

}
