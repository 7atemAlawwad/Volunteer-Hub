package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    boolean existsByEmail(String email);
    Volunteer findVolunteerById(Integer id);
}
