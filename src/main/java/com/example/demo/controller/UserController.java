package com.example.demo.controller;

import com.example.demo.services.UserService;
import com.example.demo.vo.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequestMapping("/person")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public User createNewProfil(@RequestParam String name,
                                @RequestParam String firstname,
                                @RequestParam String mail) {
        User u = new User();

        u = this.userService.saveUser(User.builder().name(name).firstname(firstname).mail(mail).build());

        return u;
    }
}
