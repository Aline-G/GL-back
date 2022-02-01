package com.expensebills.back.controller;

import com.expensebills.back.exception.ExpenseBillException;
import com.expensebills.back.exception.FunctionalException;
import com.expensebills.back.service.DateService;
import com.expensebills.back.service.ExpenseBillService;
import com.expensebills.back.vo.BillStates;
import com.expensebills.back.vo.ExpenseBill;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@RequestMapping("/expensebill")
@RestController
public class ExpenseBillController {
    @Autowired
    ExpenseBillService expenseBillService;
    @Autowired
    DateService dateService;



    @GetMapping("/new")
    public ExpenseBill createNewExpenseBill(@RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam String date) throws ExpenseBillException {
        /*
         * Function that creates a new expenseBill in the data base.
         * When an bill is created its state is DRAFT
         * @Parameter name : The name of the Bill
         * @Parameter description : a comment about the bill for the manager
         * @Parameter date : the mounth of the bill
         * @Returns : the object ExpenseBill that was created.
         * */

        expenseBillService.verifDate(date);

        return this.expenseBillService.saveExpenseBill(ExpenseBill.builder()
                .amount(0)
                .listLineBill(new ArrayList<>())
                .listAdvance(new ArrayList<>())
                .name(name)
                .date(date)
                .description(description)
                .state(BillStates.DRAFT)
                .build());
    }

    @GetMapping("/delete")
    public HttpStatus deleteExpenseBill(@RequestParam int id) throws ExpenseBillException {
        return this.expenseBillService.deleteExpenseBill(id);
    }

    @GetMapping("/list")
    public List<ExpenseBill> getExpenseBillList() {
        return this.expenseBillService.getExpenseBillList();
    }

    @GetMapping("/getwithid")
    public ExpenseBill getExpenseBillById(@RequestParam int id) throws ExpenseBillException {
        return this.expenseBillService.getExpenseBillById(id);
    }

    @GetMapping("/numberNotesNonValidated")
    public int getNumberBillsNonValidated() {
        return this.expenseBillService.getNumberBillsNonValidated();
    }

    @GetMapping("/refusal")
    public HttpStatus refuseExpenseBill(@RequestParam int expenseBillId) throws ExpenseBillException {
        return this.expenseBillService.refuseExpenseBill(expenseBillId);
    }

    @GetMapping("/sendValidation")
    public ExpenseBill sendForValidation(@RequestParam int expenseBillId) throws ExpenseBillException {
        return this.expenseBillService.sendForValidation(expenseBillId);
    }

    @GetMapping("/total")
    public float totalExpenseBill() {
        return this.expenseBillService.getTotal();
    }

    //TODO verifier que c'est bien un manager qui fait la demande
    @GetMapping("/validation")
    public HttpStatus validExpenseBill(@RequestParam int expenseBillId) throws ExpenseBillException {
        return this.expenseBillService.validExpenseBill(expenseBillId);
    }



    // TODO quand utilisateur sera codé faire des fonction de get en fonction de UserID passe en parametre

    //TRAITEMENT DES EXCEPTIONS
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<String> handleExpenseBillException(
            FunctionalException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }

}
