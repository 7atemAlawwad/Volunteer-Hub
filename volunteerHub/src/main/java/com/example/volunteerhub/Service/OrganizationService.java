package com.example.volunteerhub.Service;

import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Organization;
import com.example.volunteerhub.Repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public List<Organization> findAllOrganization() {
        return organizationRepository.findAll();
    }

    public void addOrganization(Organization org) {
        if (organizationRepository.existsByName(org.getName())) {
            throw new ApiException("Organization name already exists");
        }
        organizationRepository.save(org);
    }

    public void updateOrganization(Integer id, Organization org) {
        Organization old = organizationRepository.findOrganizationById(id);
        if (old == null) throw new ApiException("Organization not found");
        if (!old.getName().equals(org.getName()) && organizationRepository.existsByName(org.getName())) {
            throw new ApiException("Organization name already exists");
        }
        old.setName(org.getName());
        old.setDescription(org.getDescription());
        organizationRepository.save(old);
    }

    public void deleteOrganization(Integer id) {
        Organization old = organizationRepository.findOrganizationById(id);
        if (old == null) throw new ApiException("Organization not found");
        organizationRepository.delete(old);
    }
}
