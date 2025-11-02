package com.example.crmapp.controller;

import com.example.crmapp.model.ApplicationRequest;
import com.example.crmapp.model.Operators;
import com.example.crmapp.service.CoursesService;
import com.example.crmapp.service.ApplicationRequestService;
import com.example.crmapp.service.OperatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class RequestController {

    @Autowired
    private ApplicationRequestService service;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private OperatorsService operatorsService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("requests", service.getAllRequests());
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/new-requests")
    public String newRequests(Model model) {
        model.addAttribute("requests", service.getNewRequests());
        model.addAttribute("activePage", "new");
        return "index";
    }

    @GetMapping("/processed-requests")
    public String processedRequests(Model model) {
        model.addAttribute("requests", service.getProcessedRequests());
        model.addAttribute("activePage", "processed");
        return "index";
    }

    @GetMapping("/add-request")
    public String addRequest(Model model) {
        model.addAttribute("courses", coursesService.getAllCourses());
        model.addAttribute("request", new ApplicationRequest());
        model.addAttribute("activePage", "add");
        return "add-request";
    }

    @PostMapping("/save-request")
    public String saveRequest(ApplicationRequest request) {
        service.createRequest(request);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", service.getRequestById(id));
        return "details";
    }

    @PostMapping("/delete-request/{id}")
    public String deleteRequest(@PathVariable("id") Long id) {
        service.deleteRequest(id);
        return "redirect:/";
    }

    @GetMapping("/details/{id}/assign")
    public String showAssignPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("request", service.getRequestById(id));
        model.addAttribute("operators", operatorsService.getAllOperators());
        return "assign-operators";
    }

    @PostMapping("/details/{id}/assign")
    public String assignOperators(
            @PathVariable("id") Long id,
            @RequestParam(value = "operatorIds", required = false) List<Long> operatorIds) {


        if (operatorIds == null || operatorIds.isEmpty()) {
            return "redirect:/details/" + id;
        }

        service.assignOperatorsToRequest(id, operatorIds);
        return "redirect:/details/" + id;
    }

    @PostMapping("/details/{requestId}/unassign/{operatorId}")
    public String unassignOperator(
            @PathVariable("requestId") Long requestId,
            @PathVariable("operatorId") Long operatorId) {

        service.unassignOperatorFromRequest(requestId, operatorId);
        return "redirect:/details/" + requestId;
    }



    @GetMapping("/operators")
    public String showOperatorsPage(Model model) {
        model.addAttribute("operators", operatorsService.getAllOperators());
        model.addAttribute("activePage", "operators");
        return "operators";
    }

    @PostMapping("/add-operator")
    public String addOperator(Operators operator) {
        operatorsService.addOperator(operator);
        return "redirect:/operators";
    }
}