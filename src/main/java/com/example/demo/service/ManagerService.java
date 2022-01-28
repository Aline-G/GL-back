package com.example.demo.service;

import com.example.demo.repository.ManagerRepository;
import com.example.demo.vo.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ManagerService {

    @Autowired private ManagerRepository managerRepository;

    public List<Manager> getManagerList() {
        return StreamSupport.stream(this.managerRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Manager saveManager(Manager manager) {
        /*
        //On vérifie que la personne n'existe pas déjà
        if (this.existsByMail(manager.getMail())) {
            throw new PersonException("Profil déjà existant", HttpStatus.CONFLICT);
        }
        */
        return this.managerRepository.save(manager);
    }

    public HttpStatus deleteManager(int id) {
        this.managerRepository.delete(this.managerRepository.findById(id));
        return HttpStatus.OK;
    }

    // public HttpStatus changeManagerTeam(int id, Team workTeam) {
    //     this.managerRepository.findById(id).setWorkTeam(workTeam);
    //     return HttpStatus.OK;
    // }
}
