package com.example.demo.controller;

import com.example.demo.exception.FunctionalException;
import com.example.demo.exception.MissionException;
import com.example.demo.service.DateService;
import com.example.demo.service.MissionService;
import com.example.demo.vo.Mission;
import com.example.demo.vo.MissionStates;
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

    @GetMapping("/new")
    public Mission createNewMission(@RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam String dateBegining,
                                    @RequestParam String dateEnding) {

        Mission m = this.missionService.saveMission(Mission.builder()
                .name(name)
                .description(description)
                .dateBegining(dateService.parseDate(dateBegining))
                .dateEnding(dateService.parseDate(dateEnding))
                .state(MissionStates.IN_PROGRESS)
                .build());
        return m;
    }

    @GetMapping("/delete")
    public HttpStatus deleteMission(int id) throws MissionException {

        return this.missionService.deleteMission(id);
    }

    @GetMapping("/list")
    public List<Mission> getUserList() {
        return this.missionService.getMissionList();
    }

    @GetMapping("/changestate")
    public HttpStatus changeState(int idMission, String state) throws MissionException {
        return this.missionService.changeState(idMission, state);
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
