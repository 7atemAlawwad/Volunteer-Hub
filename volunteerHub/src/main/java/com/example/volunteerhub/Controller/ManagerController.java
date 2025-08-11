package com.example.volunteerhub.Controller;

import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Model.Manager;
import com.example.volunteerhub.Service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {


    private final ManagerService service;

    @GetMapping("/get")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(200).body(service.findAllManagers());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody Manager manager) {
        service.addManager(manager);
        return ResponseEntity.status(200).body(new ApiResponse("Manager added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Valid @RequestBody Manager manager) {
        service.updateManager(id, manager);
        return ResponseEntity.status(200).body(new ApiResponse("Manager updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        service.deleteManager(id);
        return ResponseEntity.status(200).body(new ApiResponse("Manager deleted"));
    }
}
