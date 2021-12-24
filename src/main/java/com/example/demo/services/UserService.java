package com.example.demo.services;

import com.example.demo.repository.UserRepository;
import com.example.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        /*
        //On vérifie que la personne n'existe pas déjà
        if (this.existsByMail(user.getMail())) {
            throw new PersonException("Profil déjà existant", HttpStatus.CONFLICT);
        }
        */
        return this.userRepository.save(user);

    }
}
