package com.example.volunteerhub.Controller;

import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Model.Organization;
import com.example.volunteerhub.Service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping("/get")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(200).body(service.findAllOrganization());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody Organization org) {
        service.addOrganization(org);
        return ResponseEntity.status(200).body(new ApiResponse("Organization added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Valid @RequestBody Organization org) {
        service.updateOrganization(id, org);
        return ResponseEntity.status(200).body(new ApiResponse("Organization updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        service.deleteOrganization(id);
        return ResponseEntity.status(200).body(new ApiResponse("Organization deleted"));
    }
}
