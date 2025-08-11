package com.example.volunteerhub.Model;
import jakarta.persistence.*;
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
@Check(constraints = "approvalStatus in ('PENDING','ACCEPTED','REJECTED')")
@Table(name = "opportunity_assignment")
public class OpportunityAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Opportunity id is required")
    @Column(columnDefinition = "int not null")
    private Integer opportunityId;

    @NotNull(message = "Volunteer id is required")
    @Column(columnDefinition = "int not null")
    private Integer volunteerId;

    @NotNull(message = "Approval status is required")
    @Column(columnDefinition = "varchar(10) not null")
    private String approvalStatus;

    @NotNull
    @Column(columnDefinition = "int not null default 0")
    private Integer earnedHours;

    @Column(insertable = false,
            updatable = false,
            columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime assignedAt;
}
