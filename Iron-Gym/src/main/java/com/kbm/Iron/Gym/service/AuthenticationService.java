package com.kbm.Iron.Gym.service;


import com.kbm.Iron.Gym.entity.Admin;
import com.kbm.Iron.Gym.entity.AuthenticationResponse;
import com.kbm.Iron.Gym.exceptions.UserNotActiveException;
import com.kbm.Iron.Gym.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(AdminRepository adminRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(Admin request) {
        try {
            Admin admin = new Admin();

            admin.setFirstName(request.getFirstName());
            admin.setLastName(request.getLastName());
            admin.setUsername(request.getUsername());
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
            admin.setRole(request.getRole());
            admin = adminRepository.save(admin);

            String token = jwtService.generateToken(admin);

            return new AuthenticationResponse(token);
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            throw new RuntimeException("Error occurred while registering user");
        }
    }

    public AuthenticationResponse authenticate(Admin request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));

            Admin user = adminRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token);
        } catch (UserNotActiveException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            throw new RuntimeException("Authentication failed");
        }
    }

}