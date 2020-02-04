package org.optaweb.employeerostering.service.otp;

import org.optaweb.employeerostering.domain.otp.OneTimePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    @Autowired
    OtpRepository otpRepository;

    public OneTimePassword createOtp(String email) {
        return otpRepository.save(new OneTimePassword(email));
    }
}
