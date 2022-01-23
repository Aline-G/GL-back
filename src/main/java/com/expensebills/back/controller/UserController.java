package com.expensebills.back.controller;

import com.expensebills.back.exception.TeamException;
import com.expensebills.back.service.ManagerService;
import com.expensebills.back.service.TeamService;
import com.expensebills.back.service.UserService;
import com.expensebills.back.vo.Manager;
import com.expensebills.back.vo.Team;
import com.expensebills.back.vo.User;
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

    @Autowired private UserService userService;
    @Autowired private ManagerService managerService;
    @Autowired private TeamService teamService;

    @GetMapping("/new")
    public User createNewProfile(@RequestParam String name, @RequestParam String firstname, @RequestParam String mail,
                                 @RequestParam int idTeam) {

        User u = null;
        try {
            u = this.userService.saveUser(User.builder().name(name).firstname(firstname).mail(mail)
                                              .workTeam(this.teamService.findById(idTeam)).build());
        } catch (TeamException e) {
            e.printStackTrace();
        }

        return u;
    }

    @GetMapping("/tempnew")
    public User createNewProfile(@RequestParam String name, @RequestParam String firstname, @RequestParam String mail,
                                 @RequestParam boolean isManager) {
        User u;
        if (isManager) u = this.userService.saveUser(User.builder().name(name).firstname(firstname).mail(mail).build());
        else u = this.userService.saveUser(Manager.builder().name(name).firstname(firstname).mail(mail).build());
        //else u = this.managerService.saveManager(Manager.builder().name(name).firstname(firstname).mail(mail).build());
        return u;
    }

    @GetMapping("/delete")
    public HttpStatus deleteUser(@RequestParam int id) {

        return this.userService.deleteUser(id);
    }

    @GetMapping("/ismanager")
    public boolean isManager(@RequestParam int id) {
        return this.userService.getUser(id) instanceof Manager;
    }

    @GetMapping("/list")
    public List<User> getUserList() {
        return this.userService.getUserList();
    }

    @GetMapping("/listmanagers")
    public List<Manager> getManagerList() {
        return this.userService.getManagerList();
    }
}
