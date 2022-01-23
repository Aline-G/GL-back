package com.expensebills.back.service;

import com.expensebills.back.repository.UserRepository;
import com.expensebills.back.vo.Manager;
import com.expensebills.back.vo.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ManagerService {

    @Autowired private UserRepository userRepository;

    // public List<Manager> getManagerList() {
    //     return StreamSupport.stream(this.userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    // }

    public Manager saveManager(Manager manager) {
        /*
        //On vérifie que la personne n'existe pas déjà
        if (this.existsByMail(manager.getMail())) {
            throw new PersonException("Profil déjà existant", HttpStatus.CONFLICT);
        }
        */
        return this.userRepository.save(manager);
    }

    public HttpStatus deleteManager(int id) {
        this.userRepository.delete(this.userRepository.findById(id));
        return HttpStatus.OK;
    }

    public HttpStatus changeManagerTeam(int id, Team workTeam) {
        this.userRepository.findById(id).setWorkTeam(workTeam);
        return HttpStatus.OK;
    }
}
