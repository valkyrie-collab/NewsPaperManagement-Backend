package com.bytetrio.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bytetrio.financial.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    // int insertCustomerDetails(@Param(""))
}
