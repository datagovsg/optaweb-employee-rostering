package org.optaweb.employeerostering.service.user;

import java.util.Optional;

import org.optaweb.employeerostering.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail (String email);
}
