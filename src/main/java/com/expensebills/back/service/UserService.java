package com.expensebills.back.service;

import com.expensebills.back.exception.UserException;
import com.expensebills.back.repository.UserRepository;
import com.expensebills.back.vo.Team;
import com.expensebills.back.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private TeamService teamService;

    public List<User> getUserList() {
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

    public HttpStatus deleteUser(int id) {
        User target = this.userRepository.findById(id);
        List<Team> teams = this.teamService.findTeamsWithLeader(id);
        if (teams != null && !teams.isEmpty()) {
            System.err.println("Attempting to delete user " + target.getId() + " which is leader of teams " +
                               Arrays.toString(teams.toArray()));
            return HttpStatus.BAD_REQUEST;
        }
        if (target != null) this.userRepository.delete(target);
        else return HttpStatus.NOT_FOUND;
        return HttpStatus.OK;
    }

    public User getUser(int id) {
        return this.userRepository.findById(id);
    }

    public HttpStatus changeUserTeam(int id, Team workTeam) {
        this.userRepository.findById(id).setWorkTeam(workTeam);
        return HttpStatus.OK;
    }

    /**
     * check given string against this regex : ^.+@.+$
     * This just assumes an email contains a à symbol match all the officialy valid addresses.
     * Further address filtering should be done with trying sending an email to the given address.
     *
     * https://codefool.tumblr.com/post/15288874550/list-of-valid-and-invalid-email-addresses
     * https://en.wikipedia.org/wiki/Email_address#Validation_and_verification
     *
     * @param mail the mail to check
     */
    public static void checkMailFormat(String mail) throws UserException {
        if (mail == null) {
            throw new UserException("Adresse mail nulle", HttpStatus.BAD_REQUEST);
        }
        Pattern macPattern = Pattern.compile("^.+@.+$");
        Matcher m = macPattern.matcher(mail.toLowerCase().strip());
        if (!m.matches()) {
            throw new UserException("Format adresse mail non valide", HttpStatus.CONFLICT);
        }
    }
}
