package com.example.demo.controller;

import com.example.demo.exception.AdvanceException;
import com.example.demo.exception.MissionException;
import com.example.demo.service.AdvanceService;
import com.example.demo.service.DateService;
import com.example.demo.service.MissionService;
import com.example.demo.vo.Advance;
import com.example.demo.vo.BillStates;
import com.example.demo.vo.ExpenseBill;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@RequestMapping("/advance")
@RestController
public class AdvanceController {
    @Autowired
    AdvanceService advanceService;
    @Autowired
    DateService dateService;
    @Autowired
    MissionService missionService;

    @GetMapping("/new")
    public Advance createNewAdvance(@RequestParam float amount,
                                        @RequestParam String description,
                                        @RequestParam int idMission) throws  MissionException, AdvanceException {
        /*
        * Function that creates a new advance in the data base.
        * When an advance is created its state is DRAFT
        * @Parameter amount : the total amount of the the advance
        * @Parameter description : a comment about the advance for the manager
        * @Parameter idMission : the mission which the advance will be related to.
        *                        The id of the mission has to be related to an already existing mission in the data base.
        * @Returns : the object Advance that was created.
        * */


        Advance a = this.advanceService.saveAdvance(Advance.builder()
                .amount(amount)
                .description(description)
                .mission(missionService.findById(idMission))
                .state(BillStates.DRAFT)
                .build());

        return a;
    }

    @GetMapping("/list")
    public List<Advance> getExpenseBillList() {
        return this.advanceService.getAdvanceList();
    }


}
