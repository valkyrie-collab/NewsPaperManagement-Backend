package com.bytetrio.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bytetrio.financial.repository.FinancialRepository;

@Service
public class FinancialService {
    private FinancialRepository financialRepository;
    @Autowired
    private void setFinancialRepository(FinancialRepository financialRepository) {
        this.financialRepository = financialRepository;
    }

    
}
