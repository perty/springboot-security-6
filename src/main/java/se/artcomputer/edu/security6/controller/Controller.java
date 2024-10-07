package se.artcomputer.edu.security6.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.artcomputer.edu.security6.model.OurUser;
import se.artcomputer.edu.security6.repository.OurUserRepo;
import se.artcomputer.edu.security6.repository.ProductRepo;

import java.util.Optional;

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
    public ResponseEntity<Object> getLoginForm(@RequestParam(name = "error", required = false) String error) {
        String formUrl = "/mylogin-form.html" + (error != null ? "?error" : "");
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", formUrl)
                .build();
    }

    @PostMapping("/users/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> saveUser(@RequestBody SaveUserRequest saveUserRequest) {
        Optional<OurUser> checkUser = ourUserRepo.findByEmail(saveUserRequest.email());
        if (checkUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        OurUser ourUser = new OurUser(saveUserRequest.email(), saveUserRequest.roles(), passwordEncoder.encode(saveUserRequest.password()));
        OurUser result = ourUserRepo.save(ourUser);
        if (result.getId() > 0) {
            return ResponseEntity.ok(new UserSingleResponse(result.getEmail(), result.getRoles()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error, User Not Saved");
    }

    @GetMapping("/products/all")
    public ResponseEntity<AllProductsResponse> getAllProducts() {
        return ResponseEntity.ok(AllProductsResponse.from(productRepo.findAll()));
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AllUsersResponse> getAllUsers() {
        return ResponseEntity.ok(AllUsersResponse.from(ourUserRepo.findAll()));
    }

    @GetMapping("/users/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<UserSingleResponse> getMyDetails() {
        OurUser byEmail = ourUserRepo.findByEmail(getLoggedInUserDetails().getUsername()).orElseThrow();
        return ResponseEntity.ok(new UserSingleResponse(byEmail.getEmail(), byEmail.getRoles()));
    }

    @PostMapping("/users/change-password")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        UserDetails userDetails = getLoggedInUserDetails();
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        OurUser user = ourUserRepo.findByEmail(userDetails.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        ourUserRepo.save(user);
        return ResponseEntity.ok("Password changed successfully");
    }

    public UserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }
}
