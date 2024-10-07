package com.learningmanagementsystems.LMSWithPermitIO.controller;

import com.learningmanagementsystems.LMSWithPermitIO.exception.UnauthorizedException;
import com.learningmanagementsystems.LMSWithPermitIO.model.RoleAssignmentRequest;
import com.learningmanagementsystems.LMSWithPermitIO.model.UserSignupRequest;
import com.learningmanagementsystems.LMSWithPermitIO.service.UserService;
import io.permit.sdk.enforcement.User;
import io.permit.sdk.openapi.models.UserRead;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public UserRead signup(@RequestBody UserSignupRequest request) {
        return userService.signup(request);
    }

    @PostMapping("/{userKey}/roles")
    public ResponseEntity<?> assignRoleToUser(
            @PathVariable String userKey,
            @RequestParam String roleKey,
            @RequestParam String tenantKey) {
        try {
            userService.assignRole(userKey, roleKey, tenantKey);
            return ResponseEntity.ok("Role assigned successfully to user " + userKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to assign role: " + e.getMessage());
        }
    }

}
