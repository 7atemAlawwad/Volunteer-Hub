package com.example.volunteerhub.Service;
import com.example.volunteerhub.API.ApiException;
import com.example.volunteerhub.Model.Opportunity;
import com.example.volunteerhub.Model.Review;
import com.example.volunteerhub.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepo;
    private final OpportunityRepository oppRepo;
    private final OpportunityAssignmentRepository assignRepo;

    private final AdminRepository adminRepo;
    private final ManagerRepository managerRepo;
    private final VolunteerRepository volunteerRepo;

    public void addReview(Integer opportunityId, Review body) {
        body.setOpportunityId(opportunityId);

        String reviewerRole = null;
        if (volunteerRepo.findVolunteerById(body.getReviewerId()) != null) {
            reviewerRole = "VOLUNTEER";
        } else if (managerRepo.findManagerById(body.getReviewerId()) != null) {
            reviewerRole = "MANAGER";
        } else if (adminRepo.findAdminById(body.getReviewerId()) != null) {
            reviewerRole = "ADMIN";
        } else {
            throw new ApiException("Reviewer not found");
        }

        String revieweeRole = null;
        if (volunteerRepo.findVolunteerById(body.getRevieweeId()) != null) {
            revieweeRole = "VOLUNTEER";
        } else if (managerRepo.findManagerById(body.getRevieweeId()) != null) {
            revieweeRole = "MANAGER";
        } else if (adminRepo.findAdminById(body.getRevieweeId()) != null) {
            revieweeRole = "ADMIN";
        } else {
            throw new ApiException("Reviewee not found");
        }

        Opportunity opp = oppRepo.findOpportunityById(opportunityId);
        if (opp == null) throw new ApiException("Opportunity not found");
        if (!"CLOSED".equals(opp.getStatus())) {
            throw new ApiException("Reviews are allowed only after opportunity is CLOSED");
        }

        if (reviewRepo.existsByReviewerIdAndRevieweeIdAndOpportunityId(
                body.getReviewerId(), body.getRevieweeId(), opportunityId)) {
            throw new ApiException("You have already reviewed this user for this opportunity");
        }

        if ("VOLUNTEER".equals(reviewerRole)) {
            if (!"MANAGER".equals(revieweeRole)) {
                throw new ApiException("Volunteer can only review a Manager");
            }
            if (!opp.getCreatorId().equals(body.getRevieweeId())) {
                throw new ApiException("Manager must be the creator of this opportunity");
            }
            boolean accepted = assignRepo
                    .existsByOpportunityIdAndVolunteerIdAndApprovalStatus(opportunityId, body.getReviewerId(), "ACCEPTED");
            if (!accepted) {
                throw new ApiException("Volunteer must have an ACCEPTED assignment on this opportunity");
            }
        } else if ("MANAGER".equals(reviewerRole)) {
            if (!"VOLUNTEER".equals(revieweeRole)) {
                throw new ApiException("Manager can only review a Volunteer");
            }
            if (!opp.getCreatorId().equals(body.getReviewerId())) {
                throw new ApiException("Only the creator manager can review volunteers of this opportunity");
            }
            boolean accepted = assignRepo
                    .existsByOpportunityIdAndVolunteerIdAndApprovalStatus(opportunityId, body.getRevieweeId(), "ACCEPTED");
            if (!accepted) {
                throw new ApiException("Volunteer must have an ACCEPTED assignment on this opportunity");
            }
        } else {
            throw new ApiException("Admin cannot create reviews");
        }

        reviewRepo.save(body);
    }

    public List<Review> listReceived(Integer userId) {
        return reviewRepo.findByRevieweeId(userId);
    }

    public List<Review> listGiven(Integer userId) {
        return reviewRepo.findByReviewerId(userId);
    }

    public double averageRating(Integer userId) {
        Double avg = reviewRepo.averageRatingForUser(userId);
        if (avg == null) {
            return 0;
        }
        return avg;
    }
}
