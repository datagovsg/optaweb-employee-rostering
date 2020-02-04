package org.optaweb.employeerostering.domain.agency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    // ************************************************************************
    // Simple getters and setters
    // ************************************************************************

    public String getEmailDomain() {
        return emailDomain;
    }
    public String getName() { return name; }
}
