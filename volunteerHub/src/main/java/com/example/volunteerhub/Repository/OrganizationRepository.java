package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Integer> {
    boolean existsByName(String name);

    Organization findOrganizationById(Integer id);
}
