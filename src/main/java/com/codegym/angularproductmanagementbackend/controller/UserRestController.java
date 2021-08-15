package com.codegym.angularproductmanagementbackend.controller;

import com.codegym.angularproductmanagementbackend.model.Role;
import com.codegym.angularproductmanagementbackend.model.User;
import com.codegym.angularproductmanagementbackend.service.role.RoleService;
import com.codegym.angularproductmanagementbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    private ResponseEntity<Iterable<User>> findAll() {
        Iterable<User> users = userService.findAll();
        if (!users.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<User> addUser(@RequestBody User user) {
        Set<Role> roleSet = new HashSet<>();
        Role userRole = roleService.findByName("ROLE_USER");
        roleSet.add(userRole);
        user.setRoles(roleSet);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }
}
