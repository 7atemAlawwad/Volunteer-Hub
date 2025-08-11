package com.example.volunteerhub.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "status in ('PENDING','APPROVED','REJECTED','CLOSED')")
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 4, max = 80, message = "Title must be 4 to 80 characters")
    @Column(columnDefinition = "varchar(80) not null")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "Description must be 10 to 500 characters")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotNull(message = "Seat limit is required")
    @Min(value = 1, message = "Seat limit must be at least 1")
    @Column(columnDefinition = "int not null")
    private Integer seatLimit;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "varchar(12) not null")
    private String status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "int not null")
    private Integer creatorId;

    @NotNull(message = "Hours is required")
    @Min(value = 1, message = "Hours must be at least 1")
    @Column(columnDefinition = "int not null")
    private Integer hours;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(columnDefinition = "int not null")
    private Integer organizationId;
}
