package com.abrahama.qttestapi.resources;

import com.abrahama.qttestapi.Constrants;
import com.abrahama.qttestapi.domain.User;
import com.abrahama.qttestapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @CrossOrigin
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Integer userId) {
        try {
            User user = userService.getUserById(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", e.getMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        try {
            List<User> userList = userService.getAllUsers();

            List<Map<String, Object>> response = userList.stream()
                    .map(user -> {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("userId", user.getUserId());
                        userMap.put("firstName", user.getFirstName());
                        userMap.put("lastName", user.getLastName());
                        userMap.put("email", user.getEmail());
                        return userMap;
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userService.ValidateUser(email, password);
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "LoggedIn successfully");
//        return new ResponseEntity<>(map, HttpStatus.OK);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
    //public String registerUser(@RequestBody Map<String, Object> userMap){
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.registerUser(firstName, lastName, email, password);
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "registered successfully");
//        return new ResponseEntity<>(map, HttpStatus.OK);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
        //return firstName + ", " + lastName + ", " + email + ", " + password;
    }

    @CrossOrigin
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable Integer userId,
            @RequestBody Map<String, String> updateMap) {
        try {
            String firstName = updateMap.get("firstName");
            String lastName = updateMap.get("lastName");
            String email = updateMap.get("email");

            userService.updateUser(userId, firstName, lastName, email);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Map<String, String>> userChangePassword(
            @PathVariable Integer userId,
            @RequestBody Map<String, String> passwordMap) {
        try {
            String newPassword = passwordMap.get("newPassword");

            userService.userChangePassword(userId, newPassword);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    private Map<String, String> generateJWTToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constrants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constrants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", user.getUserId().toString());
        return map;
    }

}
