package com.arthursena.fin_track.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arthursena.fin_track.model.User;
import com.arthursena.fin_track.model.dto.LoginResponse;
import com.arthursena.fin_track.model.dto.UserRequestLogin;
import com.arthursena.fin_track.model.dto.UserRequestRegister;
import com.arthursena.fin_track.model.enums.UserRole;
import com.arthursena.fin_track.repository.UserRepository;
import com.arthursena.fin_track.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated UserRequestLogin data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        
        return ResponseEntity.ok(new LoginResponse(token, user.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated UserRequestRegister data) {
        if (this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        User user = new User(data.name(), data.login(), encryptedPassword, UserRole.USER);

        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    
    
}
