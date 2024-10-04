package com.example.crudrapido.controller;

import com.example.crudrapido.entity.User;
import com.example.crudrapido.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Thymeleaf view for listing users
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/list"; // Thymeleaf template for listing users
    }

    // API endpoint for getting all users
    @GetMapping("/api")
    @ResponseBody
    public List<User> getAllUsersApi() {
        return userService.getAllUsers();
    }

    // Thymeleaf view for creating a new user
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create"; // Thymeleaf template for creating a user
    }

    // API endpoint for creating a user
    @PostMapping("/api")
    public ResponseEntity<User> createUserApi(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Create user via Thymeleaf form submission
    @PostMapping
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users"; // Redirect to the list of users
    }

    // Thymeleaf view for editing a user
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user", user.orElseThrow(() -> new RuntimeException("User not found")));
        return "user/edit"; // Thymeleaf template for editing a user
    }

    // API endpoint for updating a user
    @PutMapping("/api/{id}")
    public ResponseEntity<User> updateUserApi(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // Update user via Thymeleaf form submission
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/users"; // Redirect to the list of users
    }

    // API endpoint for deleting a user
    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteUserApi(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Delete user via Thymeleaf form submission
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users"; // Redirect to the list of users
    }
}
