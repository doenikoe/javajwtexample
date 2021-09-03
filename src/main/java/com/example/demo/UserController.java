package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@RestController
@RequestMapping(path="/user")
public class UserController {
    @Autowired    
    private UserRepository userRepository;

    @Autowired
	private JwtUserDetail userDetailsService;

    @Autowired
	private JwtUtil jwtUtil;

    // generate jwt token by user id
    @PostMapping("/token")
    public ResponseEntity<?> getToken (@RequestBody User user) {
        Optional<User> userData = userRepository.findById(user.getId());
        if (userData.isPresent()) {
            String userName = Integer.toString(user.getId());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            final String token = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND); 
        }
    }

    @PostMapping("")
    public User create (@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> read (@PathVariable("id") Integer id, @RequestHeader("Authorization") String authToken) {        
        String jwtToken = authToken.substring(7);
        String username = jwtUtil.getUsernameFromToken(jwtToken);
        if (Integer.parseInt(username) == id) {
            return new ResponseEntity<>(userRepository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> update (@PathVariable("id") Integer id, @RequestBody User user, @RequestHeader("Authorization") String authToken) {        
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            String jwtToken = authToken.substring(7);
            String username = jwtUtil.getUsernameFromToken(jwtToken);
            if (Integer.parseInt(username) == id) { 
                User _user = userData.get();
                _user.setName(user.getName());
                _user.setPhone(user.getPhone());
                return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") Integer id, @RequestHeader("Authorization") String authToken) {
        String jwtToken = authToken.substring(7);
        String username = jwtUtil.getUsernameFromToken(jwtToken);
        try { 
            if (Integer.parseInt(username) == id) { 
                userRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
