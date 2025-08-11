package com.example.volunteerhub.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Full name cannot be empty")
    @Size(min = 4, max = 60, message = "Full name must be 4 to 60 characters")
    @Column(columnDefinition = "varchar(60) not null")
    private String fullName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    @Column(columnDefinition = "varchar(120) not null", unique = true)
    private String email;

    @NotNull(message = "Organization id is required")
    @Column(columnDefinition = "int not null")
    private Integer organizationId;
}
