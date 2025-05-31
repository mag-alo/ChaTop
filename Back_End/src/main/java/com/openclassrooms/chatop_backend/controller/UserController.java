// package com.openclassrooms.chatop_backend.controller;

// import com.openclassrooms.chatop_backend.model.User;
// import com.openclassrooms.chatop_backend.service.UserService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {

//     private final UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     @GetMapping
//     public List<User> getAllUsers() {
//         return userService.getAllUsers();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<?> getUserById(@PathVariable Integer id) {
//         Optional<User> user = userService.getUserById(id);
//         return user.map(ResponseEntity::ok)
//                    .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
//     }

//     @PostMapping
//     public User createUser(@RequestBody User user) {
//         return userService.saveUser(user);
//     }

// }