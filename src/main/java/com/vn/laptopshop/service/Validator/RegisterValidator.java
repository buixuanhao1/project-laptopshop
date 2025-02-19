package com.vn.laptopshop.service.Validator;

import com.vn.laptopshop.domain.DTO.RegisterDTO;
import com.vn.laptopshop.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

    private UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password fields match
        if (!user.getPassword().equals(user.getconfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords must match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Additional validations can be added here

        if (this.userService.CheckEmailExists(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("email already exists")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
