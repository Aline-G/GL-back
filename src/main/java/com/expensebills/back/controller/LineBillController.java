package com.expensebills.back.controller;

import com.expensebills.back.exception.ExpenseBillException;
import com.expensebills.back.exception.FunctionalException;
import com.expensebills.back.exception.LineBillException;
import com.expensebills.back.exception.MissionException;
import com.expensebills.back.service.DateService;
import com.expensebills.back.service.ExpenseBillService;
import com.expensebills.back.service.LineBillService;
import com.expensebills.back.service.MissionService;
import com.expensebills.back.vo.LineBill;
import com.expensebills.back.vo.LineBillCategory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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
                                      @RequestParam (required = false) Float amountWithoutTaxes,
                                      @RequestParam (required = false) Float tva,
                                      @RequestParam LineBillCategory category,
                                      @RequestParam String date,
                                      @RequestParam String description,
                                      @RequestParam int idMission,
                                      @RequestParam int idExpenseBill,
                                      @RequestParam String country,
                                      @RequestParam String paymentMethod,
                                      @RequestParam Integer km,
                                      @RequestParam String restoPlace,
                                      @RequestParam String hebergementPlace,
                                      @RequestParam String vehicle,
                                      @RequestParam String conveyance,
                                      @RequestParam Integer fiscalHorsepower,
                                      @RequestParam String registrationNumber,
                                      @RequestParam File supportingDocuments,
                                      @RequestParam String guestsName) throws LineBillException, MissionException, ExpenseBillException {
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

        LineBill l = this.lineBillService.saveLineBill(LineBill.builder()
                .amount(amount)
                .isValidated(false)
                .amountWithoutTaxes(amountWithoutTaxes)
                .category(category)
                .idExpenseBill(idExpenseBill)
                .description(description)
                .mission(missionService.findById(idMission))
                .tva(tva)
                .date(dateService.parseDate(date))
                .country(country)
                .paymentMethod(paymentMethod)
                .fiscalHorsepower(fiscalHorsepower)
                .km(km)
                .registrationNumber(registrationNumber)
                .conveyance(conveyance)
                .restoPlace(restoPlace)
                .hebergementPlace(hebergementPlace)
                .vehicle(vehicle)
                .guestsName(guestsName)
                .supportingDocuments(supportingDocuments)
                .build());

        expenseBillService.addLineBill(l,idExpenseBill);

        return l;
    }

    @GetMapping("/delete")
    public HttpStatus deleteLineBill(@RequestParam int id,
                                     @RequestParam int expenseBillId) throws LineBillException, ExpenseBillException {
        return this.lineBillService.deleteLineBill(id,expenseBillId);
    }

    @GetMapping("/calculamount")
    public double getAmountMealExpense(@RequestParam Float nbKm,
                                       @RequestParam int nbFiscalHorsepower) throws LineBillException {
        return this.lineBillService.calculAmount(nbKm,nbFiscalHorsepower);
    }

    @GetMapping("/list")
    public List<LineBill> getLineBillList() {
        return this.lineBillService.getLineBillList();
    }

    @GetMapping("/listbyexpenseid")
    public List<LineBill> getLineBillListByExpenseId(@RequestParam int id) {
        return this.lineBillService.getLineBillListByIdExpense(id);
    }

    // TODO rajouter le userId dans la fonction pour s'assurer que c'est un manager qui fait la demande
    @GetMapping("/validation")
    public HttpStatus validLineBill(@RequestParam int lineBillId) throws LineBillException, ExpenseBillException {
        return this.lineBillService.validLineBill(lineBillId);
    }


    //TODO update et penser a mettre à jour les parametres de la fonction
    /*@GetMapping("/update")
    public LineBill updateLineBill(@RequestParam int id,
                                   @RequestParam (required = false)Float amount,
                                   @RequestParam (required = false) Float tvaPercent,
                                   @RequestParam (required = false) Float tva,
                                   @RequestParam (required = false)String date,
                                   @RequestParam (required = false) String country) throws LineBillException {
        LineBill lineBill = lineBillService.getLine(id);

        return lineBillService.updateLine(id);
    }*/

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
