package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Integer> {
    boolean existsByEmail(String email);
    Manager findManagerById(Integer id);

}
