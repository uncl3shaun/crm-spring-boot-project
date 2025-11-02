package com.example.crmapp.rest;

import com.example.crmapp.model.ApplicationRequest;
import com.example.crmapp.service.ApplicationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ApplicationRequestController {

    @Autowired
    private ApplicationRequestService requestService;


    @GetMapping
    public List<ApplicationRequest> getAllRequests() {
        return requestService.getAllRequests();
    }


    @GetMapping("/{id}")
    public ApplicationRequest getRequestById(@PathVariable Long id) {
        return requestService.getRequestById(id);
    }


    @PostMapping
    public ResponseEntity<ApplicationRequest> addRequest(@RequestBody ApplicationRequest request) {
        ApplicationRequest newRequest = requestService.createRequest(request);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ApplicationRequest updateRequestStatus(@PathVariable Long id, @RequestParam boolean handled) {
        return requestService.updateRequestStatus(id, handled);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.ok("Request with id " + id + " was deleted");
    }


    @PostMapping("/{id}/assign-operators")
    public ApplicationRequest assignOperators(
            @PathVariable Long id,
            @RequestBody List<Long> operatorIds) {
        return requestService.assignOperatorsToRequest(id, operatorIds);
    }


    @DeleteMapping("/{requestId}/unassign-operator/{operatorId}")
    public ApplicationRequest unassignOperator(
            @PathVariable Long requestId,
            @PathVariable Long operatorId) {
        return requestService.unassignOperatorFromRequest(requestId, operatorId);
    }

}