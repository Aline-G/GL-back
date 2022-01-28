package com.example.demo.controller;

import com.example.demo.exception.ExpenseBillException;
import com.example.demo.exception.FunctionalException;
import com.example.demo.exception.LineBillException;
import com.example.demo.exception.MissionException;
import com.example.demo.service.DateService;
import com.example.demo.service.ExpenseBillService;
import com.example.demo.service.LineBillService;
import com.example.demo.service.MissionService;
import com.example.demo.vo.LineBill;
import com.example.demo.vo.LineBillCategory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Getter
@RequestMapping("/linebill")
@RestController
public class LineBillController {
    @Autowired
    LineBillService lineBillService;
    @Autowired
    ExpenseBillService expenseBillService;
    @Autowired
    MissionService missionService;
    @Autowired
    DateService dateService;


    @GetMapping("/new")
    public LineBill createNewLineBill(@RequestParam Float amount,
                                      @RequestParam (required = false) Float tvaPercent,
                                      @RequestParam (required = false) Float tva,
                                      @RequestParam LineBillCategory category,
                                      @RequestParam String date,
                                      @RequestParam String description,
                                      @RequestParam int idMission,
                                      @RequestParam int idExpenseBill,
                                      @RequestParam String country,
                                      @RequestParam (required = false) Integer km,
                                      @RequestParam (required = false) String restoPlace,
                                      @RequestParam (required = false) String hebergementPlace,
                                      @RequestParam (required = false) String vehicle,
                                      @RequestParam (required = false) Integer fiscal_horsepower,
                                      @RequestParam (required = false) String guestsName) throws LineBillException, MissionException, ExpenseBillException {

        /*
         * Function that creates a new lineBill in the database.
         * @Parameter amount : The total amount of the line free of tva
         * @Parameter tvaPercent : (OPTIONAL) the percentage of the amount in the tva
         * @Parameter tva : (OPTIONAL) the amount of tva to pay in addition of the amount
         * @Parameter category : the category of the linebill
         * @Parameter date : The date of the line. The date must be at this format : "yyyy-mm-dd"
         * @Parameter description : a comment about the line for the manager
         * @Parameter idMission : the mission which the line will be related to.
         *                        The id of the mission has to be related to an already existing mission in the database.
         * @Parameter idExpenseBill : the expenseBill which the line will be related to.
         *                        The id of the bill has to be related to an already existing expenseBill in the database.
         * @Returns : the object ExpenseBill that was created.
         * */

        this.expenseBillService.existsById(idExpenseBill);
        this.expenseBillService.checkState(idExpenseBill);
        if(km == null){
            km = 0;
        }
        if(fiscal_horsepower == null){
            fiscal_horsepower = 0;
        }

        LineBill l = this.lineBillService.saveLineBill(LineBill.builder()
                .amount(amount)
                .isValidated(false)
                .tvaPercent(tvaPercent)
                .category(category)
                .idExpenseBill(idExpenseBill)
                .description(description)
                .mission(missionService.findById(idMission))
                .tva(tva)
                .date(dateService.parseDate(date))
                .country(country)
                .fiscal_horsepower(fiscal_horsepower)
                .km(km)
                .restoPlace(restoPlace)
                .hebergementPlace(hebergementPlace)
                .vehicle(vehicle)
                .guestsName(guestsName)
                .build());

        expenseBillService.addLineBill(l,idExpenseBill);

        return l;
    }

    @GetMapping("/delete")
    public HttpStatus deleteLineBill(@RequestParam int id) throws LineBillException {
        return this.lineBillService.deleteLineBill(id);
    }

    @GetMapping("/list")
    public List<LineBill> getLineBillList() {
        return this.lineBillService.getLineBillList();
    }

    @GetMapping("/listbyexpenseid")
    public List<LineBill> getLineBillListByExpenseId(@RequestParam int id) {
        return this.lineBillService.getLineBillListByIdExpense(id);
    }

    // TODO rajouter le userId dans la fonction pour s'assuerer que c'est un manager qui fait la demande
    @GetMapping("/validation")
    public HttpStatus validLineBill(@RequestParam int lineBillId) throws LineBillException, ExpenseBillException {
        return this.lineBillService.validLineBill(lineBillId);
    }


    //TODO update et penser a mettre à jour les parametres de la fonction
    @GetMapping("/update")
    public LineBill updateLineBill(@RequestParam int id,
                                   @RequestParam (required = false)Float amount,
                                   @RequestParam (required = false) Float tvaPercent,
                                   @RequestParam (required = false) Float tva,
                                   @RequestParam (required = false)String date,
                                   @RequestParam (required = false) String country) throws LineBillException {
        LineBill lineBill = lineBillService.getLine(id);

        return lineBillService.updateLine(id);
    }

    //TRAITEMENT DES EXCEPTIONS
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<String> handleLineBillException(
            FunctionalException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }
}
