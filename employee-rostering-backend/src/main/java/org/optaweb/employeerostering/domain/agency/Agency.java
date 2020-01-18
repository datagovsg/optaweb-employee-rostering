package org.optaweb.employeerostering.domain.agency;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(unique = true)
    private String emailDomain;

    @SuppressWarnings("unused")
    public Agency() {}

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public String getEmailDomain() {
        return emailDomain;
    }

}
