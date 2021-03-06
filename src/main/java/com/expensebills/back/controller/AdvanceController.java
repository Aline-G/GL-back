package com.expensebills.back.controller;

import com.expensebills.back.exception.*;
import com.expensebills.back.service.AdvanceService;
import com.expensebills.back.service.DateService;
import com.expensebills.back.service.MissionService;
import com.expensebills.back.vo.Advance;
import com.expensebills.back.vo.BillStates;
import com.expensebills.back.vo.ExpenseBill;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/askforvalidation")
    public Advance askForValidation(@RequestParam int id) throws AdvanceException {
        return this.advanceService.askForValidation(id);
    }

    @GetMapping("/new")
    public Advance createNewAdvance(@RequestParam float amount,
                                        @RequestParam String description,
                                        @RequestParam String name,
                                        @RequestParam int userId,
                                        @RequestParam int idMission) throws MissionException, AdvanceException {
        /*
        * Function that creates a new advance in the data base.
        * When an advance is created its state is DRAFT
        * @Parameter amount : the total amount of the the advance
        * @Parameter description : a comment about the advance for the manager
        * @Parameter idMission : the mission which the advance will be related to.
        *                        The id of the mission has to be related to an already existing mission in the data base.
        * @Returns : the object Advance that was created.
        * */

        return this.advanceService.saveAdvance(Advance.builder()
                .amount(amount)
                .date(LocalDate.now())
                .description(description)
                .name(name)
                .userId(userId)
                .mission(missionService.findById(idMission))
                .state(BillStates.DRAFT)
                .build());
    }

    @GetMapping("/delete")
    public HttpStatus deleteLineBill(@RequestParam int id) throws AdvanceException {
        return this.advanceService.deleteAdvance(id);
    }

    @GetMapping("/list")
    public List<Advance> getAdvanceBillList() {
        return this.advanceService.getAdvanceList();
    }

    @GetMapping("/listbyuserid")
    public List<Advance> getAdvanceListByUserId(@RequestParam int userId) {
        return this.advanceService.getAdvanceListByUserId(userId);
    }

    @GetMapping("/refusal")
    public HttpStatus refuseAdvance(@RequestParam int advanceId) throws AdvanceException {
        return this.advanceService.refuseExpenseBill(advanceId);
    }

    @GetMapping("/validation")
    public Advance validation(@RequestParam int advanceId) throws AdvanceException {
        return this.advanceService.validation(advanceId);
    }


    //TRAITEMENT DES EXCEPTIONS
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<String> handleAdvanceException(
            FunctionalException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }
}
