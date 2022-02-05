package com.expensebills.back.controller;

import com.expensebills.back.exception.TeamException;
import com.expensebills.back.exception.UserException;
import com.expensebills.back.service.TeamService;
import com.expensebills.back.service.UserService;
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
    @Autowired private TeamService teamService;

    @GetMapping("/new")
    public User createNewProfile(@RequestParam String name, @RequestParam String firstname, @RequestParam String mail) {
        try {
            UserService.checkMailFormat(mail);
        } catch (UserException e) {
            e.printStackTrace();
        }

        return this.userService.saveUser(User.builder().name(name).firstname(firstname).mail(mail).build());
    }

    @GetMapping("/delete")
    public HttpStatus deleteUser(@RequestParam int id) {
        return this.userService.deleteUser(id);
    }

    @GetMapping("/ismanager")
    public boolean isManager(@RequestParam int id) {
        List<Team> teams = this.teamService.findTeamsWithLeader(id);
        if (teams == null) return false;
        return !teams.isEmpty() && teams.get(0).getLeader().getId() == id; // technically this check is redundant
    }

    @GetMapping("/changeteam")
    public HttpStatus changeTeam(@RequestParam int userId, @RequestParam int teamId) {
        try {
            this.userService.getUser(userId).setWorkTeam(this.teamService.findById(teamId));
            return HttpStatus.OK;
        } catch (TeamException e) {
            e.printStackTrace();
        }
        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/list")
    public List<User> getUserList() {
        return this.userService.getUserList();
    }
}
