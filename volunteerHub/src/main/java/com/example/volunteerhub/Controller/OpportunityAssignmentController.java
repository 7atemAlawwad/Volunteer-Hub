package com.example.volunteerhub.Controller;


import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Service.OpportunityAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/opportunity-Assignment")
@RequiredArgsConstructor

public class OpportunityAssignmentController {

    private final OpportunityAssignmentService service;

    @PostMapping("/opportunities/{opportunityId}/assign/volunteer/{volunteerId}")
    public ResponseEntity<Object> request(@PathVariable Integer opportunityId,
                                          @PathVariable Integer volunteerId) {
        service.requestAssignment(opportunityId, volunteerId);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment request submitted"));
    }

    @PatchMapping("/assignments/{assignmentId}/accept/manager/{managerId}")
    public ResponseEntity<Object> accept(@PathVariable Integer assignmentId,
                                         @PathVariable Integer managerId) {
        service.accept(assignmentId, managerId);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment accepted"));
    }

    @PatchMapping("/assignments/{assignmentId}/reject/manager/{managerId}")
    public ResponseEntity<Object> reject(@PathVariable Integer assignmentId,
                                         @PathVariable Integer managerId) {
        service.reject(assignmentId, managerId);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment rejected"));
    }

    @GetMapping("/opportunities/{opportunityId}/assignments")
    public ResponseEntity<Object> listByOpportunity(@PathVariable Integer opportunityId) {
        return ResponseEntity.status(200).body(service.listByOpportunity(opportunityId));
    }

    @GetMapping("/users/{userId}/assignments")
    public ResponseEntity<Object> listByVolunteer(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(service.listByVolunteer(userId));
    }
}
