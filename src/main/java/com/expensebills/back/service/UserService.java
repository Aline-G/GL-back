package com.expensebills.back.service;

import com.expensebills.back.repository.UserRepository;
import com.expensebills.back.vo.Manager;
import com.expensebills.back.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public List<User> getUserList() {
        return StreamSupport.stream(this.userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<Manager> getManagerList() {
        return StreamSupport.stream(this.userRepository.findAll().spliterator(), false).collect(
                Collectors.filtering(user -> user instanceof Manager,
                                     Collectors.mapping(user -> (Manager) user, Collectors.toList())));
    }

    public User saveUser(User user) {
        /*
        //On vérifie que la personne n'existe pas déjà
        if (this.existsByMail(user.getMail())) {
            throw new PersonException("Profil déjà existant", HttpStatus.CONFLICT);
        }
        */
        return this.userRepository.save(user);
    }

    public HttpStatus deleteUser(int id) {
        this.userRepository.delete(this.userRepository.findById(id));
        return HttpStatus.OK;
    }

    public User getUser(int id) {
        return this.userRepository.findById(id);
    }

    public HttpStatus changeUserTeam(int id, Team workTeam) {
        this.userRepository.findById(id).setWorkTeam(workTeam);
        return HttpStatus.OK;
    }
}
