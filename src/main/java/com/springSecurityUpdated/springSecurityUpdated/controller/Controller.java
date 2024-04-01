package com.springSecurityUpdated.springSecurityUpdated.controller;

import com.springSecurityUpdated.springSecurityUpdated.model.OurUser;
import com.springSecurityUpdated.springSecurityUpdated.repository.OurUserRepo;
import com.springSecurityUpdated.springSecurityUpdated.repository.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {
    private final OurUserRepo ourUserRepo;
    private final ProductRepo productRepo;
    private final PasswordEncoder passwordEncoder;

    public Controller(OurUserRepo ourUserRepo, ProductRepo productRepo, PasswordEncoder passwordEncoder) {
        this.ourUserRepo = ourUserRepo;
        this.productRepo = productRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/mylogin")
    public ResponseEntity<Object> getLoginForm(@RequestParam(name="error", required = false) String error){
        String formUrl = "/mylogin-form.html" + (error != null ? "?error" : "");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", formUrl)
                .build();
    }

    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody OurUser ourUser) {
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        OurUser result = ourUserRepo.save(ourUser);
        if (result.getId() > 0) {
            return ResponseEntity.ok("User Was Saved");
        }
        return ResponseEntity.status(404).body("Error, User Not Saved");
    }

    @GetMapping("/product/all")
    public ResponseEntity<Object> getAllProducts() {
        return ResponseEntity.ok(productRepo.findAll());
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(ourUserRepo.findAll());
    }

    @GetMapping("/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails() {
        return ResponseEntity.ok(ourUserRepo.findByEmail(getLoggedInUserDetails().getUsername()));
    }

    public UserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
