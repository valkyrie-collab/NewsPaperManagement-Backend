package com.bytetrio.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bytetrio.authentication.model.User;
import com.bytetrio.authentication.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;
    @Autowired
    private void setUserRepo(UserRepository userRepo) {this.userRepo = userRepo;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("USER-404");
        }

        return CustomUserDetails.initialize(user);

    }

}
