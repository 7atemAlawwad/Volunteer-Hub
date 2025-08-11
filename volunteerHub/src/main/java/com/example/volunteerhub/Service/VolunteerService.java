package com.example.volunteerhub.Service;
import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Volunteer;
import com.example.volunteerhub.Repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public List<Volunteer> findAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public void addVolunteer(Volunteer volunteer) {
        if (volunteerRepository.existsByEmail(volunteer.getEmail())) {
            throw new ApiException("Email already exists");
        }
        volunteerRepository.save(volunteer);
    }

    public void updateVolunteer(Integer id, Volunteer volunteer) {
        Volunteer old = volunteerRepository.findVolunteerById(id);
        if (old == null) {
            throw new ApiException("Volunteer not found");
        }
        if (!old.getEmail().equals(volunteer.getEmail())
                && volunteerRepository.existsByEmail(volunteer.getEmail())) {
            throw new ApiException("Email already exists");
        }

        old.setFullName(volunteer.getFullName());
        old.setEmail(volunteer.getEmail());
        volunteerRepository.save(old);
    }

    public void deleteVolunteer(Integer id) {
        Volunteer old = volunteerRepository.findVolunteerById(id);
        if (old == null) {
            throw new ApiException("Volunteer not found");
        }
        volunteerRepository.delete(old);
    }
}
