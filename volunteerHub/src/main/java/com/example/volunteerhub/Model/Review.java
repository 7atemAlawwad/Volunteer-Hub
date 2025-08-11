package com.example.volunteerhub.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "rating >= 1 AND rating <= 5")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Reviewer id is required")
    @Column(columnDefinition = "int not null")
    private Integer reviewerId;

    @NotNull(message = "Reviewee id is required")
    @Column(columnDefinition = "int not null")
    private Integer revieweeId;

    @NotNull(message = "Opportunity id is required")
    @Column(columnDefinition = "int not null")
    private Integer opportunityId;

    @NotNull(message = "Rating is required")
    @Min(1) @Max(5)
    @Column(columnDefinition = "int not null")
    private Integer rating;

    @NotEmpty(message = "Comment cannot be empty")
    @Column(columnDefinition = "varchar(400) not null")
    private String comment;

    @Column(insertable = false,
            updatable = false,
            columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
