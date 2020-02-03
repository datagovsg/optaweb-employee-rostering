package org.optaweb.employeerostering.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.optaweb.employeerostering.domain.agency.Agency;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Email
    private String email; // TODO: how to enforce against Agency.emailDomain?

    @ManyToOne
    private Agency agency;

    /* Spring Security */
    @NotNull
    private Boolean enabled;

    public User (Agency agency, String email) {
        this.agency = agency;
        this.email = email;
        this.enabled = false;
    }

    public String getEmail() {
        return this.email;
    }

    @Transient
    public String getEmailDomain() {
        String[] emailParts = email.split("@");
        return emailParts.length > 1 ? emailParts[1] : null;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enable) {
        this.enabled = enable;
    }
}
