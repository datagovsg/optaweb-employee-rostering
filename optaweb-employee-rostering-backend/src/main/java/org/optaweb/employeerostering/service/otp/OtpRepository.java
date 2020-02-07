package org.optaweb.employeerostering.service.otp;

import org.optaweb.employeerostering.domain.otp.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OneTimePassword, Integer> {


}
