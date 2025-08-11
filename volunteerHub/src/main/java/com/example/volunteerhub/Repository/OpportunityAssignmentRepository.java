package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.OpportunityAssignment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpportunityAssignmentRepository extends JpaRepository<OpportunityAssignment,Integer> {

    boolean existsByOpportunityIdAndVolunteerId(Integer opportunityId, Integer volunteerId);

    long countByOpportunityIdAndApprovalStatus(Integer opportunityId, String approvalStatus);

    List<OpportunityAssignment> findByOpportunityId(Integer opportunityId);

    List<OpportunityAssignment> findByVolunteerId(Integer volunteerId);
    boolean existsByOpportunityIdAndVolunteerIdAndApprovalStatus(Integer opportunityId, Integer volunteerId, String status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
       update OpportunityAssignment a
          set a.earnedHours = :hours
        where a.opportunityId = :opportunityId
          and a.approvalStatus = 'ACCEPTED'
          and (a.earnedHours is null or a.earnedHours = 0)
    """)
    int creditAcceptedVolunteers(@Param("opportunityId") Integer opportunityId,
                                 @Param("hours") Integer hours);

    @Query("""
  select (count(a) > 0)
  from OpportunityAssignment a
  where a.volunteerId = :volunteerId
    and a.id <> :excludeId
    and (a.approvalStatus = 'PENDING' or a.approvalStatus = 'ACCEPTED')
""")
    boolean existsActiveByVolunteerExcluding(@Param("volunteerId") Integer volunteerId,
                                             @Param("excludeId") Integer excludeId);

    @Query("""
  select (count(a) > 0)
  from OpportunityAssignment a
  where a.volunteerId = :volunteerId
    and (a.approvalStatus = 'PENDING' or a.approvalStatus = 'ACCEPTED')
""")
    boolean hasActiveAssignment(Integer volunteerId);

}
