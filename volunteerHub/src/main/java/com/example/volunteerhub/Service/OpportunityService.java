package com.example.volunteerhub.Service;
import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Manager;
import com.example.volunteerhub.Model.Opportunity;
import com.example.volunteerhub.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;
    private final OpportunityAssignmentRepository assignmentRepository;

    public List<Opportunity> findAllOpportunities() {
        return opportunityRepository.findAll();
    }

    public void addOpportunity(Integer managerId, Opportunity in) {
        Manager manager = managerRepository.findManagerById(managerId);
        if (manager == null) throw new ApiException("Manager not found");

        if (in.getSeatLimit() == null || in.getSeatLimit() < 1)
            throw new ApiException("Seat limit must be at least 1");
        if (in.getHours() == null || in.getHours() < 1)
            throw new ApiException("Hours must be at least 1");

        Opportunity op = new Opportunity();
        op.setTitle(in.getTitle());
        op.setDescription(in.getDescription());
        op.setSeatLimit(in.getSeatLimit());
        op.setHours(in.getHours());
        op.setStatus("PENDING");
        op.setCreatorId(managerId);
        op.setOrganizationId(manager.getOrganizationId());

        opportunityRepository.save(op);
    }

    public void updateOpportunity(Integer id, Integer managerId, Opportunity in) {
        Opportunity old = opportunityRepository.findOpportunityById(id);
        if (old == null) throw new ApiException("Opportunity not found");
        if (!managerId.equals(old.getCreatorId()))
            throw new ApiException("Only the creator manager can update this opportunity");
        if ("CLOSED".equals(old.getStatus()))
            throw new ApiException("Closed opportunity cannot be updated");

        if (in.getSeatLimit() == null || in.getSeatLimit() < 1)
            throw new ApiException("Seat limit must be at least 1");
        if (in.getHours() == null || in.getHours() < 1)
            throw new ApiException("Hours must be at least 1");

        old.setTitle(in.getTitle());
        old.setDescription(in.getDescription());
        old.setSeatLimit(in.getSeatLimit());
        old.setHours(in.getHours());
        opportunityRepository.save(old);
    }

    public void deleteOpportunity(Integer id) {
        Opportunity old = opportunityRepository.findOpportunityById(id);
        if (old == null) throw new ApiException("Opportunity not found");
        opportunityRepository.delete(old);
    }

    public void approveOpportunity(Integer id, Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin not found");

        Opportunity op = opportunityRepository.findOpportunityById(id);
        if (op == null) throw new ApiException("Opportunity not found");
        if (!"PENDING".equals(op.getStatus()))
            throw new ApiException("Only PENDING opportunity can be approved");

        op.setStatus("APPROVED");
        opportunityRepository.save(op);
    }

    public void rejectOpportunity(Integer id, Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin not found");

        Opportunity op = opportunityRepository.findOpportunityById(id);
        if (op == null) throw new ApiException("Opportunity not found");
        if (!"PENDING".equals(op.getStatus()))
            throw new ApiException("Only PENDING opportunity can be rejected");

        op.setStatus("REJECTED");
        opportunityRepository.save(op);
    }

    public void complete(Integer opportunityId, Integer managerId) {
        Opportunity op = opportunityRepository.findOpportunityById(opportunityId);
        if (op == null) throw new ApiException("Opportunity not found");
        if (!managerId.equals(op.getCreatorId()))
            throw new ApiException("Only the creator manager can complete this opportunity");

        String s = op.getStatus();
        if (!"APPROVED".equals(s) && !"CLOSED".equals(s)) {
            throw new ApiException("Only APPROVED or CLOSED opportunity can be completed");
        }

        assignmentRepository.creditAcceptedVolunteers(opportunityId, op.getHours());

        if (!"CLOSED".equals(op.getStatus())) {
            op.setStatus("CLOSED");
        }
        opportunityRepository.save(op);
    }

}
