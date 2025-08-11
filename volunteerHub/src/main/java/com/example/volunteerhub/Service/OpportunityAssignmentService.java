package com.example.volunteerhub.Service;

import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Opportunity;
import com.example.volunteerhub.Model.OpportunityAssignment;
import com.example.volunteerhub.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpportunityAssignmentService {


    private final OpportunityAssignmentRepository assignmentRepo;
    private final OpportunityRepository opportunityRepo;
    private final VolunteerRepository volunteerRepo;
    private final ManagerRepository managerRepo;

    public List<OpportunityAssignment> listByOpportunity(Integer opportunityId) {
        return assignmentRepo.findByOpportunityId(opportunityId);
    }

    public List<OpportunityAssignment> listByVolunteer(Integer volunteerId) {
        return assignmentRepo.findByVolunteerId(volunteerId);
    }

    public void requestAssignment(Integer opportunityId, Integer volunteerId) {
        // volunteer & opportunity checks as before...
        if (volunteerRepo.findVolunteerById(volunteerId) == null)
            throw new ApiException("Volunteer not found");

        Opportunity opp = opportunityRepo.findOpportunityById(opportunityId);
        if (opp == null) throw new ApiException("Opportunity not found");

        if (!"APPROVED".equals(opp.getStatus())) {
            throw new ApiException("Opportunity is not open for requests");
        }

        // block if already full
        long accepted = assignmentRepo.countByOpportunityIdAndApprovalStatus(opp.getId(), "ACCEPTED");
        if (accepted >= opp.getSeatLimit()) {
            throw new ApiException("Opportunity is closed (full)");
        }

        if (assignmentRepo.hasActiveAssignment(volunteerId)) {
            throw new ApiException("Volunteer already has an active assignment");
        }


        if (assignmentRepo.existsByOpportunityIdAndVolunteerId(opportunityId, volunteerId)) {
            throw new ApiException("Already requested/assigned to this opportunity");
        }

        OpportunityAssignment a = new OpportunityAssignment();
        a.setOpportunityId(opportunityId);
        a.setVolunteerId(volunteerId);
        a.setApprovalStatus("PENDING");
        a.setEarnedHours(0);
        assignmentRepo.save(a);
    }


    public void accept(Integer assignmentId, Integer managerId) {
        if (managerRepo.findManagerById(managerId) == null)
            throw new ApiException("Manager not found");

        OpportunityAssignment a = assignmentRepo.findById(assignmentId).orElse(null);
        if (a == null) throw new ApiException("Assignment not found");
        if (!"PENDING".equals(a.getApprovalStatus()))
            throw new ApiException("Only PENDING assignment can be accepted");

        Opportunity opp = opportunityRepo.findOpportunityById(a.getOpportunityId());
        if (opp == null) throw new ApiException("Opportunity not found");
        if (!managerId.equals(opp.getCreatorId()))
            throw new ApiException("Only the creator manager can accept this assignment");
        if (!"APPROVED".equals(opp.getStatus()))
            throw new ApiException("Opportunity must be APPROVED to accept assignments");

        // capacity pre-check
        long acceptedBefore = assignmentRepo.countByOpportunityIdAndApprovalStatus(opp.getId(), "ACCEPTED");
        if (acceptedBefore >= opp.getSeatLimit()) {
            throw new ApiException("Seat limit reached");
        }

        // one-active-at-a-time re-check for the volunteer (exclude this assignment)
        if (assignmentRepo.existsActiveByVolunteerExcluding(a.getVolunteerId(), a.getId())) {
            throw new ApiException("Volunteer already has another active assignment");
        }

        // accept
        a.setApprovalStatus("ACCEPTED");
        assignmentRepo.save(a);

        // auto-close when full
        long acceptedAfter = assignmentRepo.countByOpportunityIdAndApprovalStatus(opp.getId(), "ACCEPTED");
        if (acceptedAfter >= opp.getSeatLimit() && !"CLOSED".equals(opp.getStatus())) {
            opp.setStatus("CLOSED");
            opportunityRepo.save(opp);
        }
    }


    /** MANAGER (creator) rejects */
    public void reject(Integer assignmentId, Integer managerId) {
        if (managerRepo.findManagerById(managerId) == null)
            throw new ApiException("Manager not found");

        OpportunityAssignment a = assignmentRepo.findById(assignmentId).orElse(null);
        if (a == null) throw new ApiException("Assignment not found");
        if (!"PENDING".equals(a.getApprovalStatus()))
            throw new ApiException("Only PENDING assignment can be rejected");

        Opportunity opp = opportunityRepo.findOpportunityById(a.getOpportunityId());
        if (opp == null) throw new ApiException("Opportunity not found");
        if (!managerId.equals(opp.getCreatorId()))
            throw new ApiException("Only the creator manager can reject this assignment");

        a.setApprovalStatus("REJECTED");
        assignmentRepo.save(a);
    }

}
