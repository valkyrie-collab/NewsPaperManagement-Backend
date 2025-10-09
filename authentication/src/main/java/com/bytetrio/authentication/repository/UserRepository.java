package com.bytetrio.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytetrio.authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
