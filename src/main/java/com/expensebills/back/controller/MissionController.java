package com.expensebills.back.controller;

import com.expensebills.back.exception.DateException;
import com.expensebills.back.exception.FunctionalException;
import com.expensebills.back.exception.MissionException;
import com.expensebills.back.service.DateService;
import com.expensebills.back.service.MissionService;
import com.expensebills.back.vo.Mission;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequestMapping("/mission")
@RestController
public class MissionController {

    @Autowired
    MissionService missionService;
    @Autowired
    DateService dateService;

    @GetMapping("/changestate")
    public HttpStatus changeState(int idMission, String state) throws MissionException {
        return this.missionService.changeState(idMission, state);
    }

    @GetMapping("/new")
    public Mission createNewMission(@RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam String dateBegining,
                                    @RequestParam String dateEnding) throws DateException {

        LocalDate dateB = dateService.parseDate(dateBegining) ;
        LocalDate dateE = dateService.parseDate(dateEnding);

        this.dateService.checkDateMission(dateB, dateE);

        return this.missionService.saveMission(Mission.builder()
                .name(name)
                .description(description)
                .dateBegining(dateB)
                .dateEnding(dateE)
                .state(this.missionService.getState(dateB, dateE))
                .build());
    }

    @GetMapping("/delete")
    public HttpStatus deleteMission(int id) throws MissionException {

        return this.missionService.deleteMission(id);
    }

    @GetMapping("/list")
    public List<Mission> getUserList() {
        return this.missionService.getMissionList();
    }



    //TRAITEMENT DES EXCEPTIONS
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<String> handleMissionException(
            FunctionalException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }
}
