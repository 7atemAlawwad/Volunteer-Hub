package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpportunityRepository extends JpaRepository<Opportunity,Integer> {
    Opportunity findOpportunityById(Integer id);
}
