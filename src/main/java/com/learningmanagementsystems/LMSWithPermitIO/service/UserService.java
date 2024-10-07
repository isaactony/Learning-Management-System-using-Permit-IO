package com.learningmanagementsystems.LMSWithPermitIO.service;

import com.learningmanagementsystems.LMSWithPermitIO.exception.ForbiddenAccessException;
import com.learningmanagementsystems.LMSWithPermitIO.exception.UnauthorizedException;
import com.learningmanagementsystems.LMSWithPermitIO.model.UserSignupRequest;
import io.permit.sdk.Permit;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.api.PermitContextError;
import io.permit.sdk.api.models.CreateOrUpdateResult;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import io.permit.sdk.openapi.models.UserRead;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.permit.sdk.openapi.models.TenantCreate;
import io.permit.sdk.openapi.models.TenantRead;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final Permit permit;

    public UserService(Permit permit) {
        this.permit = permit;
    }


    public User login(String key) {
        return new User.Builder(key).build();
    }

    public UserRead signup(UserSignupRequest request) {
        String slugifiedKey = slugifyKey(request.getKey());
        User user = new User.Builder(slugifiedKey)
                .withEmail(request.getEmail())
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withAttributes(request.getAttributes())
                .build();

        try {
            permit.api.users.sync(user);
            CreateOrUpdateResult<UserRead> result = permit.api.users.sync(user);
            return result.getResult();
        } catch (PermitApiError e) {
            logger.error("Permit.io API Error: " + e.getMessage());
            logger.error("Status code: " + e.getStackTrace());
            logger.error("Error details: " + e.getRawResponse());
            throw new RuntimeException("Failed to create user in Permit.io", e);
        } catch (Exception e) {
            logger.error("Unexpected error during user signup: " + e.getMessage());
            throw new RuntimeException("Failed to create user", e);
        }
    }

    private String slugifyKey(String key) {
        return key.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }


    public TenantRead createTenant(String tenantKey, String tenantName) throws Exception {
        try {
            // Check if tenant already exists
            TenantRead existingTenant = permit.api.tenants.get(tenantKey);
            if (existingTenant != null) {
                throw new Exception("Tenant with key '" + tenantKey + "' already exists.");
            }
        } catch (PermitApiError apiError) {
            if (apiError.getResponseCode() != 404) {
                throw new Exception("Error checking tenant existence: " + apiError.getMessage());
            }
            // Continue to tenant creation if tenant is not found (404)
        }

        try {
            // Create tenant if it does not exist
            TenantRead newTenant = permit.api.tenants.create(
                    new TenantCreate(tenantKey, tenantName)
            );
            logger.info("Tenant '{}' created successfully with key '{}'", tenantName, tenantKey);
            return newTenant;
        } catch (PermitApiError | IOException e) {
            logger.error("Error creating tenant: {}", e.getMessage());
            throw new Exception("Failed to create tenant", e);
        }
    }

    public void assignRole(String userKey, String roleKey, String tenantKey) throws Exception {
        try {
            // Assign role to the user in the tenant
            permit.api.users.assignRole(userKey, roleKey, tenantKey);
            logger.info("Successfully assigned role '{}' to user '{}' in tenant '{}'", roleKey, userKey, tenantKey);
        } catch (PermitApiError apiError) {
            logger.error("Permit.io API Error while assigning role '{}' to user '{}' in tenant '{}': {}", roleKey, userKey, tenantKey, apiError.getMessage());
            throw new Exception("Failed to assign role: " + apiError.getMessage());
        } catch (IOException e) {
            logger.error("IO Exception while assigning role '{}' to user '{}' in tenant '{}': {}", roleKey, userKey, tenantKey, e.getMessage());
            throw new Exception("IO Error occurred while assigning role", e);
        }
    }


    public void authorize(User user, String action, Resource resource) {
        if (user == null) {
            throw new UnauthorizedException("Not logged in, signup and try again");
        }
        try {
            boolean permitted = permit.check(user, action, resource);
            if (!permitted) {
                throw new ForbiddenAccessException("Access denied");
            }
        } catch (PermitApiError | IOException e) {
            throw new RuntimeException("Failed to authorize user", e);
        }
    }
}

