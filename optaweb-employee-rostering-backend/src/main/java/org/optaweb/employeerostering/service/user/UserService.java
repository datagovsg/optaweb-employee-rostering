package org.optaweb.employeerostering.service.user;

import java.util.Optional;

import org.optaweb.employeerostering.domain.agency.Agency;
import org.optaweb.employeerostering.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        Assert.notNull(userRepository, "userRepository must not be null");
    }

    public User getOrCreateUser(Agency agency, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        return userOptional.orElseGet(() -> userRepository.save(new User(agency, email)));
    }

}
