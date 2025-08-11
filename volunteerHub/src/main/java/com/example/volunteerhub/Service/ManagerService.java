package com.example.volunteerhub.Service;

import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Manager;
import com.example.volunteerhub.Repository.ManagerRepository;
import com.example.volunteerhub.Repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ManagerService {


    private final ManagerRepository managerRepository;
    private final OrganizationRepository organizationRepository;

    public List<Manager> findAllManagers() {
        return managerRepository.findAll();
    }

    public void addManager(Manager manager) {
        if (managerRepository.existsByEmail(manager.getEmail())) {
            throw new ApiException("Email already exists");
        }
        if (manager.getOrganizationId() == null || !organizationRepository.existsById(manager.getOrganizationId())) {
            throw new ApiException("Organization not found");
        }
        managerRepository.save(manager);
    }

    public void updateManager(Integer id, Manager manager) {
        Manager old = managerRepository.findManagerById(id);
        if (old == null) throw new ApiException("Manager not found");

        if (!old.getEmail().equals(manager.getEmail()) &&
                managerRepository.existsByEmail(manager.getEmail())) {
            throw new ApiException("Email already exists");
        }

        if (manager.getOrganizationId() == null ||
                !organizationRepository.existsById(manager.getOrganizationId())) {
            throw new ApiException("Organization not found");
        }

        old.setFullName(manager.getFullName());
        old.setEmail(manager.getEmail());
        old.setOrganizationId(manager.getOrganizationId());
        managerRepository.save(old);
    }

    public void deleteManager(Integer id) {
        Manager old = managerRepository.findManagerById(id);
        if (old == null) throw new ApiException("Manager not found");
        managerRepository.delete(old);
    }
}
