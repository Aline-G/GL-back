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

    @Autowired TeamRepository teamRepository;

    public Team saveTeam(Team team) throws TeamException {
        if (this.teamRepository.existsById(team.getId())) {
            throw new TeamException("Id team already exist", HttpStatus.CONFLICT);
        }

        return this.teamRepository.save(team);
    }

    public HttpStatus deleteTeam(int id) {
        Team target = this.teamRepository.findById(id);
        if (target != null) this.teamRepository.delete(target);
        else return HttpStatus.NOT_FOUND;
        return HttpStatus.OK;
    }

    public List<Team> getTeamList() {
        return StreamSupport.stream(this.teamRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Team findById(int id) throws TeamException {
        if (!this.teamRepository.existsById(id)) {
            throw new TeamException("Team " + id + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        return this.teamRepository.findById(id);
    }
}
