package com.example.volunteerhub.Controller;
import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Model.Volunteer;
import com.example.volunteerhub.Service.VolunteerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/volunteers")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService service;

    @GetMapping("/get")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(200).body(service.findAllVolunteers());
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@Valid @RequestBody Volunteer volunteer) {
        service.addVolunteer(volunteer);
        return ResponseEntity.status(200).body(new ApiResponse("Volunteer added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @Valid @RequestBody Volunteer volunteer) {
        service.updateVolunteer(id, volunteer);
        return ResponseEntity.status(200).body(new ApiResponse("Volunteer updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        service.deleteVolunteer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Volunteer deleted"));
    }
}
