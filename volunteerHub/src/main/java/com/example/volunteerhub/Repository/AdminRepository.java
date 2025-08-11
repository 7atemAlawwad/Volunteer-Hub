package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    boolean existsByEmail(String email);
    Admin findAdminById(Integer id);
}
