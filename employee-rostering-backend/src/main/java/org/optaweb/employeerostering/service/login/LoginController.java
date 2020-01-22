package org.optaweb.employeerostering.service.login;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@CrossOrigin
@Validated
@Api(tags="Login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @ApiOperation("Login/registration for a user that belongs to a registered agency")
    @PostMapping("/otp")
    public ResponseEntity<Void> generateOtp(@Valid @Email @RequestParam("email") String email) {
        try {
            loginService.loginOrRegisterNewUser(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
