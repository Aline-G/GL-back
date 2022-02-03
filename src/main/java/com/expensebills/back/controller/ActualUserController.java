package com.expensebills.back.controller;


import com.expensebills.back.exception.ActualUserException;
import com.expensebills.back.service.ActualUserService;
import com.expensebills.back.vo.ActualUser;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Getter
@RequestMapping("/actualuser")
@RestController
public class ActualUserController {

    @Autowired
    ActualUserService actualUserService;

    @GetMapping("/new")
    public HttpStatus updateCurrentUser(@RequestParam int id) throws Exception {

        this.actualUserService.deleteAll();

        this.actualUserService.saveCurrentUser(ActualUser.builder()
                .idUser(id)
                .build());

        return HttpStatus.OK;
    }

    @GetMapping("/get")
    public int getActualUser() throws ActualUserException {

        return this.actualUserService.getActualUser();
    }

}
