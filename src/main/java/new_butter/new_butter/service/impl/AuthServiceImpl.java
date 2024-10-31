package new_butter.new_butter.service.impl;

import new_butter.new_butter.domain.Role;
import new_butter.new_butter.domain.User;
import new_butter.new_butter.domain.enums.ERole;
import new_butter.new_butter.domain.enums.UserStatus;
import new_butter.new_butter.exception.api_exception.ApiMessageException;
import new_butter.new_butter.payload.request.LoginRequest;
import new_butter.new_butter.payload.request.SignupRequest;
import new_butter.new_butter.payload.request.UsernameRequest;
import new_butter.new_butter.payload.response.JwtResponse;
import new_butter.new_butter.payload.response.MessageResponse;
import new_butter.new_butter.repository.RoleRepository;
import new_butter.new_butter.repository.UserRepository;
import new_butter.new_butter.security.impl.UserDetailsImpl;
import new_butter.new_butter.security.jwt.JwtUtils;
import new_butter.new_butter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

//    @PostConstruct
//    public void createAnSuperAdminAccount() {
//        Optional<User> superAdminAccount = userRepository.findByRoles(ERole.ROLE_SUPER_ADMIN);
//        if (superAdminAccount.isEmpty()) {
//            User user = new User();
//            user.setUsername("admin");
//            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
//            user.setEmail("admin@gmail.com");
//            user.setStatus(UserStatus.ACTIVE);
//            Set<Role> roles = new HashSet<>();
//            roles.add(roleRepository.findByName(ERole.ROLE_SUPER_ADMIN));
//            user.setRoles(roles);
//            userRepository.save(user);
//            System.out.println("Admin account created successfully");
//        } else {
//            System.out.println("Admin account already exist");
//        }
//    }

    @Override
    public JwtResponse authenticateUser(LoginRequest request) {
        // TODO
        /**
         * Step 1: Kiem tra tinh hop le cua request
         */
        if (request.getUsername() == null) {
            throw new ApiMessageException("Username is empty");
        }
        if (request.getPassword() == null) {
            throw new ApiMessageException("Password is empty");
        }

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getStatus().equals(UserStatus.IN_ACTIVE)) {
                throw new ApiMessageException("In-active User");
            }
        }

        /**
         * Step2: xu ly
         */
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        /**
         * Step3: return
         *     1: convert Entity to DTO (response)
         *     2: return response
         */
        return JwtResponse.build(jwt, userDetails.getId(), userDetails.getFullname(),  userDetails.getAddress(),userDetails.getUsername(),userDetails.getEmail(), userDetails.getDateOfBirth(),roles);
    }

    @Override
    public MessageResponse registerUser(SignupRequest request) {
        // TODO
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiMessageException("Error: Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiMessageException("Error: Email already exists");
        }

        User user = new User(request.getUsername(), request.getEmail(),
                encoder.encode(request.getPassword()));

        user.setStatus(UserStatus.ACTIVE);

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);
            if (userRole == null) {
                userRole = new Role();
                userRole.setName(ERole.ROLE_USER);
                userRole = roleRepository.save(userRole);
            }
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                        roles.add(adminRole);
                        break;
                    case "super_admin":
                        Role superAdminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN);
                        roles.add(superAdminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(UsernameRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()) {
            throw new ApiMessageException("User not found");
        }

        return optionalUser.get();
    }
}
