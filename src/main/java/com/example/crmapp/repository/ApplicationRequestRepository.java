package com.example.crmapp.repository;

import com.example.crmapp.model.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {


    List<ApplicationRequest> findByHandled(boolean handled);


}