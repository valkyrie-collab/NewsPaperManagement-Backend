package com.bytetrio.financial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bytetrio.financial.model.CustomerFinancial;
import com.bytetrio.financial.model.Subscription;

@Repository
public interface FinancialRepository extends JpaRepository<CustomerFinancial, String> {
    
    @Transactional
    @Modifying
    @Query(value = "update customer_financial set cancel = TRUE where id = :subscriptionId and customer_id = :customerId", nativeQuery = true)
    int updateSubscription(@Param("subscriptionId") String subscriptionId, @Param("customerId") String customerId); 

    @Query(value = "select case when exists ( select 1 from customer_financial where customer_id = :customerId and id = :id) then true else false end", nativeQuery = true)
    boolean checkParticularCustomerFinancial(@Param("customerId") String customerId, @Param("id") String id);

    @Query(value = "select case when exists (select 1 from subscription where customer_financial_id = :customerId) then true else false end", nativeQuery = true)
    boolean checkSubscriptions(@Param("customerId") String customerId);

    @Query(value = "select * from customer_financial where customer_id = :customerId and id = :id", nativeQuery = true)
    CustomerFinancial getCustomerFinancial(@Param("customerId") String customerId, @Param("id") String id);

    @Query(value = "select * from subscription where customer_financial_id = :customerId", nativeQuery = true)
    List<Subscription> getSubscriptions(@Param("customerId") String customerId);

    @Modifying
    @Transactional
    @Query(value = "insert into subscription_customer_financial ( customer_financial_id, subscription_id ) values (:username, :id)", nativeQuery = true)
    void insetSubscriptions(@Param("username") String username, @Param("id") String id);
}
