package com.bytetrio.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bytetrio.api.model.User;
// import com.bytetrio.authentication.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    // private UserRepository userRepo;
    // @Autowired
    // private void setUserRepo(UserRepository userRepo) {this.userRepo = userRepo;}

    private AuthenticationFeignController feign;
    @Autowired
    private void setFeign(AuthenticationFeignController feign) {this.feign = feign;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<User> response = feign.getUser(username);

        if (response == null) {throw new UsernameNotFoundException("USER-404");}
        
        User user = response.getBody();

        if (user == null) {
            throw new UsernameNotFoundException("USER-404");
        }

        return CustomUserDetails.initialize(user);

    }

}
