package com.api.itsec_test.controller;

import com.api.itsec_test.dto.AuthResponseDto;
import com.api.itsec_test.dto.LoginDto;
import com.api.itsec_test.dto.RegisterDto;
import com.api.itsec_test.models.Role;
import com.api.itsec_test.models.UserModel;
import com.api.itsec_test.repository.RoleRepository;
import com.api.itsec_test.repository.UserRepository;
import com.api.itsec_test.security.JwtGenerator;
import com.api.itsec_test.service.SchemaSwitcherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;
    private SchemaSwitcherService schemaSwitcherService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                          JwtGenerator jwtGenerator, SchemaSwitcherService schemaSwitcherService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.schemaSwitcherService = schemaSwitcherService;
    }

    @PostMapping("/{schema}/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto,
                                           @PathVariable("schema") String schemaName){
        boolean schemaExists = schemaSwitcherService.schemaExists(schemaName);
        if(!schemaExists){
            return new ResponseEntity<>("Schema Doesn't Exists", HttpStatus.NOT_FOUND);
        }
        schemaSwitcherService.switchSchema(schemaName);
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        UserModel user = new UserModel();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);

        return new ResponseEntity<>("Register Success", HttpStatus.OK);
    }

    @PostMapping("/{schema}/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterDto registerDto,
                                                @PathVariable("schema") String schemaName){
        boolean schemaExists = schemaSwitcherService.schemaExists(schemaName);
        if(!schemaExists){
            return new ResponseEntity<>("Schema Doesn't Exists", HttpStatus.NOT_FOUND);
        }
        schemaSwitcherService.switchSchema(schemaName);
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        UserModel user = new UserModel();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role roles = roleRepository.findByName("ADMIN").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);

        return new ResponseEntity<>("Admin Register Success", HttpStatus.OK);
    }

    @PostMapping("/{schema}/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto,
                                                 @PathVariable("schema") String schemaName){
        boolean schemaExists = schemaSwitcherService.schemaExists(schemaName);
        if(!schemaExists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        schemaSwitcherService.switchSchema(schemaName);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

}
