package com.bytetrio.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bytetrio.customer.model.Customer;
import com.bytetrio.customer.model.CustomerNameAddress;
import com.bytetrio.customer.model.Image;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Modifying
    @Transactional
    @Query(value = "update customer set first_name = :updatedValue where id = :id", nativeQuery = true)
    int updateFirstName(@Param("updatedValue") String updatedValue, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update customer set second_name = :updatedValue where id = :id", nativeQuery = true)
    int updateSecondName(@Param("updatedValue") String updatedValue, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update customer set bio = :updatedValue where id = :id", nativeQuery = true)
    int updateBio(@Param("updatedValue") String updatedValue, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update customer set phone_number = :updatedValue where id = :id", nativeQuery = true)
    int updatePhoneNumber(@Param("updatedValue") long updatedValue, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update customer set email = :updatedValue where id = :id", nativeQuery = true)
    int updateEmail(@Param("updatedValue") String updatedValue, @Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "update customer set address = :updatedValue where id = :id", nativeQuery = true)
    int updateAddress(@Param("updatedValue") String updatedValue, @Param("id") String id);

    @Transactional
    @Query(value = "select * from image where customer_id = :customerId", nativeQuery = true)
    Image getProfileImage(@Param("customerId") String customerId);

    @Query(value = "select 1 from image where customer_id = :customerId", nativeQuery = true)
    Integer checkImages(@Param("customerId") String customerId);

    @Query(value = "select first_name, second_name, address, phone_number from customer", nativeQuery = true)
    List<CustomerNameAddress> getNameAddressPhoneNumber();

}
