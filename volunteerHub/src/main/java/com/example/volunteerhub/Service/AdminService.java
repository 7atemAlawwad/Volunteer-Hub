package com.example.volunteerhub.Service;
import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Admin;
import com.example.volunteerhub.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final AdminRepository adminRepository;

    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin) {
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new ApiException("Email already exists");
        }
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id, Admin admin) {
        Admin old = adminRepository.findAdminById(id);
        if (old == null) {
            throw new ApiException("Admin not found");
        }
        if (!old.getEmail().equals(admin.getEmail())
                && adminRepository.existsByEmail(admin.getEmail())) {
            throw new ApiException("Email already exists");
        }

        old.setFullName(admin.getFullName());
        old.setEmail(admin.getEmail());
        adminRepository.save(old);
    }

    public void deleteAdmin(Integer id) {
        Admin old = adminRepository.findAdminById(id);
        if (old == null) {
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(old);
    }
}
