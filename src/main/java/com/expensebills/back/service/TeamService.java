package com.expensebills.back.service;

import com.expensebills.back.exception.TeamException;
import com.expensebills.back.repository.TeamRepository;
import com.expensebills.back.vo.Team;
import com.expensebills.back.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeamService {

    // TODO manager part of an other service ?

    @Autowired TeamRepository teamRepository;
    @Autowired UserService userService;

    public Team saveTeam(Team team) throws TeamException {
        if (this.teamRepository.existsById(team.getId())) {
            throw new TeamException("Id team already exist", HttpStatus.CONFLICT);
        }

        return this.teamRepository.save(team);
    }

    public HttpStatus deleteTeam(int id) {
        Team target = this.teamRepository.findById(id);
        if (target != null) this.teamRepository.delete(
                target); // TODO BROKEN : constraint failure, fixing it after removing Manager class
        else return HttpStatus.NOT_FOUND;
        return HttpStatus.OK;
    }

    public List<Team> getTeamList() {
        return StreamSupport.stream(this.teamRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Team> findTeamsWithLeader(int idLeader) {
        return StreamSupport.stream(this.teamRepository.findAll().spliterator(), false).collect(
                Collectors.filtering(team -> team.getLeader() != null && team.getLeader().getId() == idLeader,
                                     Collectors.toList()));
    }

    public List<User> getLeadersList() {
        return StreamSupport.stream(this.teamRepository.findAll().spliterator(), false).map(Team::getLeader)
                            .collect(Collectors.toList());
    }

    public Team findById(int id) throws TeamException {
        if (!this.teamRepository.existsById(id)) {
            throw new TeamException("Team " + id + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        return this.teamRepository.findById(id);
    }

    public HttpStatus updateManager(int teamId, int managerId) {
        List<Team> teams = findTeamsWithLeader(managerId);
        if (teams != null && !teams.isEmpty()) {
            System.err.println("User " + managerId + " is already a team leader.");
            return HttpStatus.BAD_REQUEST;
        }
        try {
            findById(teamId).setLeader(userService.getUser(managerId));
        } catch (TeamException e) {
            e.printStackTrace();
        }
        return HttpStatus.OK;
    }

    // public void validateBill(ExpenseBill bill) {
    //     if (billsToValidate.contains(bill)) {
    //         // TODO other stuff
    //         bill.setState(BillStates.VALIDATED);
    //         billsToValidate.remove(bill);
    //     } else {
    //         // TODO error ?
    //     }
    // }
}
