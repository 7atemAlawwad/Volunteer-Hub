package com.example.volunteerhub.Repository;

import com.example.volunteerhub.Model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    boolean existsByReviewerIdAndRevieweeIdAndOpportunityId(Integer reviewerId, Integer revieweeId, Integer opportunityId);
    List<Review> findByRevieweeId(Integer revieweeId);
    List<Review> findByReviewerId(Integer reviewerId);

    @Query("""
       select coalesce(avg(r.rating), 0)
       from Review r
       where r.revieweeId = :userId
    """)
    Double averageRatingForUser(@Param("userId") Integer userId);
}
