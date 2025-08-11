package com.example.volunteerhub.Controller;
import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Model.Opportunity;
import com.example.volunteerhub.Service.OpportunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/opportunities")
@RequiredArgsConstructor
public class OpportunityController {
    private final OpportunityService service;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(service.findAllOpportunities());
    }

    @PostMapping("/add/manager/{managerId}")
    public ResponseEntity<?> add(@PathVariable Integer managerId,
                                      @Valid @RequestBody Opportunity body) {
        service.addOpportunity(managerId, body);
        return ResponseEntity.status(200).body(new ApiResponse("Opportunity created"));
    }

    @PutMapping("/update/{id}/manager/{managerId}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                         @PathVariable Integer managerId,
                                         @Valid @RequestBody Opportunity body) {
        service.updateOpportunity(id, managerId, body);
        return ResponseEntity.status(200).body(new ApiResponse("Opportunity updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.deleteOpportunity(id);
        return ResponseEntity.status(200).body(new ApiResponse("Opportunity deleted"));
    }

    @PatchMapping("/{id}/approve/admin/{adminId}")
    public ResponseEntity<Object> approve(@PathVariable Integer id,
                                          @PathVariable Integer adminId) {
        service.approveOpportunity(id, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Opportunity approved"));
    }

    @PatchMapping("/{id}/reject/admin/{adminId}")
    public ResponseEntity<Object> reject(@PathVariable Integer id,
                                         @PathVariable Integer adminId) {
        service.rejectOpportunity(id, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Opportunity rejected"));
    }

    @PatchMapping("/{id}/complete/manager/{managerId}")
    public ResponseEntity<Object> complete(@PathVariable Integer id,
                                           @PathVariable Integer managerId) {
        service.complete(id, managerId);
        return ResponseEntity.status(200).body(new ApiResponse("Opportunity completed and hours credited"));
    }
}
