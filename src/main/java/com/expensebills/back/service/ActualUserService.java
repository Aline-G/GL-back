package com.expensebills.back.service;

import com.expensebills.back.exception.ActualUserException;
import com.expensebills.back.repository.ActualUserRepository;
import com.expensebills.back.vo.ActualUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ActualUserService {
    @Autowired
    ActualUserRepository actualUserRepository;

    public ActualUser saveCurrentUser(ActualUser actualUser) {
        return this.actualUserRepository.save(actualUser);
    }

    public HttpStatus deleteAll(){
        actualUserRepository.deleteAll();
        return HttpStatus.OK;
    }

    public int getActualUser() throws ActualUserException {

        for (ActualUser actualUser : StreamSupport.stream(this.actualUserRepository.findAll().spliterator(), false).collect(Collectors.toList())){
            return actualUser.getIdUser();
        }
       throw new ActualUserException("No current user declared", HttpStatus.CONFLICT);
    }
}
