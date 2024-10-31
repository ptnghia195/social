package new_butter.new_butter.service;

import new_butter.new_butter.domain.User;
import new_butter.new_butter.payload.request.LoginRequest;
import new_butter.new_butter.payload.request.SignupRequest;
import new_butter.new_butter.payload.request.UsernameRequest;
import new_butter.new_butter.payload.response.JwtResponse;
import new_butter.new_butter.payload.response.MessageResponse;

import java.util.List;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest request);
    MessageResponse registerUser(SignupRequest request);
    List<User> getAllUsers();
    User getUserByUsername(UsernameRequest request);
}