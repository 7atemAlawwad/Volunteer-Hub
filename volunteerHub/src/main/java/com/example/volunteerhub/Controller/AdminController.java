package com.example.volunteerhub.Controller;


import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Model.Admin;
import com.example.volunteerhub.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService service;

    @GetMapping("/get")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(200).body(service.findAllAdmins());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody Admin admin) {
        service.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Valid @RequestBody Admin admin) {
        service.updateAdmin(id, admin);
        return ResponseEntity.status(200).body(new ApiResponse("Admin updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        service.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("Admin deleted"));
    }
}
