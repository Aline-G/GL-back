package com.expensebills.back.controller;

import com.expensebills.back.exception.TeamException;
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
@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired private TeamService teamService;
    @Autowired private UserService userService;

    @GetMapping("/newfull")
    public Team createNewServiceFull(@RequestParam String name, @RequestParam int managerId) {

        Team t = null;
        try {
            User user = this.userService.getUser(managerId);
            if (user != null) {
                List<Team> teams = this.teamService.findTeamsWithLeader(managerId);
                if (teams != null && !teams.isEmpty()) {
                    System.err.println("User " + managerId + " is already a team leader.");
                } else t = this.teamService.saveTeam(Team.builder().name(name).leader(user).build());
            } else System.err.println("User " + managerId + " doesn't exist");
        } catch (TeamException e) {
            e.printStackTrace();
        }
        return t;
    }

    @GetMapping("/new")
    public Team createNewService(@RequestParam String name) {
        Team t = null;
        try {
            t = this.teamService.saveTeam(Team.builder().name(name).build());
        } catch (TeamException e) {
            e.printStackTrace();
        }
        return t;
    }

    @GetMapping("/delete")
    public HttpStatus deleteTeam(@RequestParam int id) {
        return this.teamService.deleteTeam(id);
    }

    @GetMapping("/changemanager")
    public HttpStatus updateManager(@RequestParam int teamId, @RequestParam int managerId) {
        return this.teamService.updateManager(teamId, managerId);
    }

    @GetMapping("/list")
    public List<Team> getUserList() {
        return this.teamService.getTeamList();
    }

}
