package org.optaweb.employeerostering.service.agency;

import java.util.Optional;

import org.optaweb.employeerostering.domain.agency.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {

    public Optional<Agency> findByEmailDomain(String emailDomain);
}
