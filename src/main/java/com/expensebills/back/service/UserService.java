package com.expensebills.back.service;

import com.expensebills.back.exception.UserException;
import com.expensebills.back.repository.UserRepository;
import com.expensebills.back.vo.Manager;
import com.expensebills.back.vo.Team;
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
        User target = this.userRepository.findById(id);
        if (target != null) this.userRepository.delete(target);
        else return HttpStatus.NOT_FOUND;
        return HttpStatus.OK;
    }

    public User getUser(int id) {
        return this.userRepository.findById(id);
    }

    public Manager getManager(int id) throws UserException {
        User result = this.userRepository.findById(id);
        if (!(result instanceof Manager))
            throw new UserException("User with id " + id + " is not a manager.", HttpStatus.NOT_ACCEPTABLE);
        return (Manager) result;
    }

    public HttpStatus changeUserTeam(int id, Team workTeam) {
        this.userRepository.findById(id).setWorkTeam(workTeam);
        return HttpStatus.OK;
    }
}
