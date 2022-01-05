package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getPersonList() {
        return StreamSupport.stream(this.userRepository.findAll().spliterator(), false).collect(Collectors.toList());
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

    public HttpStatus deletePerson(int id) {

        this.userRepository.delete(this.userRepository.findById(id));
        return HttpStatus.OK;
    }
}
