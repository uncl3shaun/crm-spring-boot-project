package com.example.crmapp.service;
import com.example.crmapp.exception.ResourceNotFoundException;
import com.example.crmapp.model.ApplicationRequest;
import com.example.crmapp.model.Courses;
import com.example.crmapp.model.Operators;
import com.example.crmapp.repository.ApplicationRequestRepository;
import com.example.crmapp.repository.CoursesRepository;
import com.example.crmapp.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationRequestService {

    @Autowired
    private ApplicationRequestRepository repository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private OperatorsRepository operatorsRepository;

    public ApplicationRequest createRequest(ApplicationRequest request) {
        request.setOperators(new ArrayList<>());
        return repository.save(request);
    }


    public ApplicationRequest assignOperatorsToRequest(Long requestId, List<Long> operatorIds) {
        ApplicationRequest request = getRequestById(requestId);


        if (request.isHandled()) {
            throw new IllegalStateException("Request with id " + requestId + " is already processed.");
        }

        List<Operators> operators = operatorsRepository.findAllById(operatorIds);

        request.setOperators(operators);
        request.setHandled(true);
        return repository.save(request);
    }

    public ApplicationRequest unassignOperatorFromRequest(Long requestId, Long operatorId) {
        ApplicationRequest request = getRequestById(requestId);
        Operators operator = operatorsRepository.findById(operatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Operator not found with id: " + operatorId));

        request.getOperators().remove(operator);



        return repository.save(request);
    }


    public List<ApplicationRequest> getAllRequests() {
        return repository.findAll();
    }

    public List<ApplicationRequest> getNewRequests() {
        return repository.findByHandled(false);
    }

    public List<ApplicationRequest> getProcessedRequests() {
        return repository.findByHandled(true);
    }

    public ApplicationRequest getRequestById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found with id: " + id));
    }




    public ApplicationRequest updateRequestStatus(Long id, boolean handled) {

        ApplicationRequest request = getRequestById(id);
        request.setHandled(handled);
        return repository.save(request);
    }


    public void deleteRequest(Long id) {
        ApplicationRequest request = getRequestById(id);
        repository.delete(request);
    }
}