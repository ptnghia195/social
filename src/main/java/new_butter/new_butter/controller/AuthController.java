package new_butter.new_butter.controller;

import jakarta.validation.Valid;
import new_butter.new_butter.payload.request.LoginRequest;
import new_butter.new_butter.payload.request.SignupRequest;
import new_butter.new_butter.payload.request.UsernameRequest;
import new_butter.new_butter.payload.response.JwtResponse;
import new_butter.new_butter.payload.response.MessageResponse;
import new_butter.new_butter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authService.authenticateUser(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(this.authService.registerUser(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(this.authService.getAllUsers());
    }

    @PostMapping("/getByUsername")
    public ResponseEntity<?> getUsername(@RequestBody UsernameRequest request) {
        return ResponseEntity.ok(this.authService.getUserByUsername(request));
    }
}