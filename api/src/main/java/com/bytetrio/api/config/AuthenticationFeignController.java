package com.bytetrio.api.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bytetrio.api.model.AuthenticationByPass;
import com.bytetrio.api.model.User;

@FeignClient(value = "AUTHENTICATION", configuration = AuthenticationByPass.class)
public interface AuthenticationFeignController {

    @GetMapping("/user/get-user")
    public ResponseEntity<User> getUser(@RequestParam String username);

}
