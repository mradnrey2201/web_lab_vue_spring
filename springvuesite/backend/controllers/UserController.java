package springvuesite.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springvuesite.backend.JWT.JwtResponse;
import springvuesite.backend.JWT.JwtUtil;
import springvuesite.backend.models.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            User encodeUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
            userRepository.save(encodeUser);
            System.out.println("User " + user.getUsername() + " registered");
            return new ResponseEntity<>(encodeUser, HttpStatus.CREATED);
        } else {
            System.out.println("Username is already taken");
            return new ResponseEntity<>(
                    new RuntimeException("Username :" + user.getUsername() + ", is already taken"),
                    HttpStatus.CONFLICT
            );
        }
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> authUser(@RequestBody User user) {
        try {
            User checkUser = userRepository.findByUsername(user.getUsername());
            if (checkUser == null) throw new BadCredentialsException("No user with this username");
            if (!passwordEncoder.matches(user.getPassword(), checkUser.getPassword())) throw new BadCredentialsException("Bad password");
            String jwt = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername()));
        } catch(BadCredentialsException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
