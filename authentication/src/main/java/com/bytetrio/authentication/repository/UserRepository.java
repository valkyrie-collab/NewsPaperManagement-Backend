package com.bytetrio.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bytetrio.authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "select 1 from users where username = :username", nativeQuery = true)
    Integer checkIfUserPresent(@Param("username") String username);

}
