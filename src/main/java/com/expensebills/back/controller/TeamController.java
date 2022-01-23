package com.expensebills.back.controller;

import com.expensebills.back.exception.TeamException;
import com.expensebills.back.exception.UserException;
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
@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired private TeamService teamService;
    @Autowired private UserService userService;

    @GetMapping("/new")
    public Team createNewService(@RequestParam String name, @RequestParam int idManager) {

        Team t = null;
        try {
            t = this.teamService.saveTeam(Team.builder().name(name).leader(this.userService.getManager(idManager)).build());
        } catch (TeamException | UserException e) {
            e.printStackTrace();
        }

        return t;
    }

    @GetMapping("/delete")
    public HttpStatus deleteTeam(@RequestParam int id) {
        return this.teamService.deleteTeam(id);
    }

    @GetMapping("/list")
    public List<Team> getUserList() {
        return this.teamService.getTeamList();
    }

}
