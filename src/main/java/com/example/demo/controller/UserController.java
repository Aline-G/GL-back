package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.vo.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public User createNewProfil(@RequestParam String name,
                                @RequestParam String firstname,
                                @RequestParam String mail) {

        User u = this.userService.saveUser(User.builder().name(name).firstname(firstname).mail(mail).build());

        return u;
    }

    @GetMapping("/delete")
    public HttpStatus deleteUser(int id) {

        return this.userService.deleteUser(id);
    }

    @GetMapping("/list")
    public List<User> getUserList() {
        return this.userService.getUserList();
    }
}
