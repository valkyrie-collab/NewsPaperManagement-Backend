package com.bytetrio.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytetrio.financial.model.CustomerFinancial;

@Repository
public interface FinancialRepository extends JpaRepository<CustomerFinancial, String> {

}
