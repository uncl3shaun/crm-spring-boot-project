package com.example.crmapp.service;

import com.example.crmapp.exception.ResourceNotFoundException;
import com.example.crmapp.model.Operators;
import com.example.crmapp.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorsService {

    @Autowired
    private OperatorsRepository operatorsRepository;


    public List<Operators> getAllOperators() {
        return operatorsRepository.findAll();
    }

    public Operators addOperator(Operators operator) {
        return operatorsRepository.save(operator);
    }

    public Operators getOperatorById(Long id) {
        return operatorsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + id));
    }
}