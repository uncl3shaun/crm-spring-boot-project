package com.example.crmapp.rest;


import com.example.crmapp.model.Operators;
import com.example.crmapp.service.OperatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorsController {

    @Autowired
    private OperatorsService operatorsService;


    @GetMapping
    public List<Operators> getAllOperators() {
        return operatorsService.getAllOperators();
    }


    @PostMapping
    public ResponseEntity<Operators> addOperator(@RequestBody Operators operator) {
        Operators newOperator = operatorsService.addOperator(operator);
        return new ResponseEntity<>(newOperator, HttpStatus.CREATED);
    }

}