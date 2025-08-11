package com.example.volunteerhub.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Organization name cannot be empty")
    @Size(min = 3, max = 80, message = "Organization name must be 3 to 80 characters")
    @Column(columnDefinition = "varchar(80) not null")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "Description must be 10 to 500 characters")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;


}
