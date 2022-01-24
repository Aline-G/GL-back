package com.example.demo.controller;

import com.example.demo.exception.FunctionalException;
import com.example.demo.exception.MissionException;
import com.example.demo.service.DateService;
import com.example.demo.service.MissionService;
import com.example.demo.vo.Mission;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                    @RequestParam String date) {

        Mission m = this.missionService.saveMission(Mission.builder()
                .name(name)
                .description(description)
                .date(dateService.parseDate(date))
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
