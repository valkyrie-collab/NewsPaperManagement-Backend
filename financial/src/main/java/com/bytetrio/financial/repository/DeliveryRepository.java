package com.bytetrio.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bytetrio.financial.model.DeliveryFinancial;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryFinancial, String> {

    @Modifying
    @Transactional
    @Query(value = "update delivery_financial set total_delivery = :number, total_value_delivery = :value where id = :id ", nativeQuery = true)
    int updateDeliveryFinance(@Param("id") String id, @Param("number") int number, @Param("value") double value);

    @Query(value = "select case when exists (select 1 from delivery_financial where customer_id = :id) then true else false end", nativeQuery = true)
    boolean checkDeliveryFinance(@Param("customerId") String customerId);

    void deleteAllByCustomerId(String customerId);
}
