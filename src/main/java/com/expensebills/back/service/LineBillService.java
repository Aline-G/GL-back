package com.expensebills.back.service;

import com.expensebills.back.exception.ExpenseBillException;
import com.expensebills.back.exception.LineBillException;
import com.expensebills.back.repository.LineBillRepository;
import com.expensebills.back.vo.BillStates;
import com.expensebills.back.vo.ExpenseBill;
import com.expensebills.back.vo.LineBill;
import com.expensebills.back.vo.LineBillSates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LineBillService {
    @Autowired
    LineBillRepository lineBillRepository;
    @Autowired
    ExpenseBillService expenseBillService;

    public double calculAmount(Float nbKm, int nbFiscalHorsepower) throws LineBillException {
        double amount;
        if(nbFiscalHorsepower<= 3){
            if(nbKm<= 5000){
                amount = nbKm*0.456;
            }else if(nbKm>5000 && nbKm <= 20000){
                amount = (nbKm*0.273)+915;
            }else{
                amount = nbKm*0.318;
            }
        }else if(nbFiscalHorsepower == 4){
            if(nbKm<= 5000){
                amount = nbKm*0.523;
            }else if(nbKm>5000 && nbKm <= 20000){
                amount = (nbKm*0.294)+1147;
            }else{
                amount = nbKm*0.352;
            }
        }else if(nbFiscalHorsepower == 5){
            if(nbKm<= 5000){
                amount = nbKm*0.548;
            }else if(nbKm>5000 && nbKm <= 20000){
                amount = (nbKm*0.308)+1200;
            }else{
                amount = nbKm*0.368;
            }
        }else if(nbFiscalHorsepower == 6){
            if(nbKm<= 5000){
                amount = nbKm*0.574;
            }else if(nbKm>5000 && nbKm <= 20000){
                amount = (nbKm*0.323)+1256;
            }else{
                amount = nbKm*0.386;
            }
        }else {
            if(nbKm<= 5000){
                amount = nbKm*0.601;
            }else if(nbKm>5000 && nbKm <= 20000){
                amount = (nbKm*0.340)+1301;
            }else{
                amount = nbKm*0.405;
            }
        }
        if(amount == 0){
            throw new LineBillException("Issue : some entries are wrong", HttpStatus.BAD_REQUEST);
        }
        return amount;
    }

    private void checkStateExpenseBill(int idExpenseBill) throws ExpenseBillException {
        if(this.expenseBillService.getState(idExpenseBill) == BillStates.VALIDATED){
            throw new ExpenseBillException("This expenseBill is already validated", HttpStatus.CONFLICT);
        }
        else if(this.expenseBillService.getState(idExpenseBill) == BillStates.WAITING){
            throw new ExpenseBillException("This expenseBill is waiting for validation and can't be modified", HttpStatus.CONFLICT);
        }
    }

    public HttpStatus deleteLineBill(int id, int expenseBillId) throws LineBillException, ExpenseBillException {
        //Check existence of this id
        if(!lineBillRepository.existsById(id)){
            throw new LineBillException("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        ExpenseBill e = this.expenseBillService.getExpenseBillById(expenseBillId);
        List<LineBill> finalList = new ArrayList<>();
        float newAmount = e.getAmount();
        for (LineBill l : e.getListLineBill()){
            if(l.getId()!=id){
                finalList.add(l);
            }else{
                newAmount-= l.getAmount();
            }
        }
        this.expenseBillService.getExpenseBillById(expenseBillId).setListLineBill(finalList);
        this.expenseBillService.getExpenseBillById(expenseBillId).setAmount(newAmount);

        this.lineBillRepository.delete(this.lineBillRepository.findById(id));
        return HttpStatus.OK;
    }

    public LineBill getLine(int id){
        return lineBillRepository.findById(id);
    }

    public List<LineBill> getLineBillList() {
        return StreamSupport.stream(this.lineBillRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<LineBill> getLineBillListByIdExpense(int id) {
        return new ArrayList<>(this.lineBillRepository.findAllByIdExpenseBill(id));
    }

    public LineBill saveLineBill(LineBill lineBill) throws LineBillException, ExpenseBillException {

        // On check l'etat de l'expense bill
        checkStateExpenseBill(lineBill.getIdExpenseBill());

        // check the existence of TVA data
        /*if(lineBill.getTva() == null && lineBill.getTvaPercent() == null){
            throw new LineBillException("Informations about TVA are missing", HttpStatus.BAD_REQUEST);
        }

        //TVA Calcul
        if(lineBill.getTva() == null || lineBill.getTvaPercent() == null){
            lineBill = calculTVA(lineBill);
        }*/

        //Check the id of the line
        if (this.lineBillRepository.existsById(lineBill.getId())) {
            throw new LineBillException("Id line already exist", HttpStatus.CONFLICT);
        }
        return this.lineBillRepository.save(lineBill);
    }

    //TODO update
    /*public LineBill updateLine(int id) throws LineBillException {
        LineBill lineBill = lineBillRepository.findById(id);
        //Test if line already validated
        if(lineBill.isValidated()){
            throw new LineBillException("LineBill already validated", HttpStatus.CONFLICT);
        }

        //Check id exists
        if (!this.lineBillRepository.existsById(lineBill.getId())) {
            throw new LineBillException("Impossible to update an inexisting lineBill", HttpStatus.CONFLICT);
        }
        // check the existence of TVA data
       *//* if(lineBill.getTva() == null && lineBill.getTvaPercent() == null){
            throw new LineBillException("Informations about TVA are missing", HttpStatus.BAD_REQUEST);
        }

        //TVA Calcul
        if(lineBill.getTva() == null || lineBill.getTvaPercent() == null){
            lineBill = calculTVA(lineBill);
        }*//*

        return this.lineBillRepository.save(lineBill);
    }*/

    // TODO verifier que c'est bien un manager qui fait la demande
    public HttpStatus validLineBill(int lineBillId) throws LineBillException, ExpenseBillException {
        if (!this.lineBillRepository.existsById(lineBillId)) {
            throw new LineBillException("Impossible to update an inexisting lineBill", HttpStatus.CONFLICT);
        }
        LineBill lineBill = lineBillRepository.findById(lineBillId);
        lineBill.setState(LineBillSates.VALIDATED);
        lineBillRepository.save(lineBill);

        if(expenseBillService.allValidated(lineBill.getIdExpenseBill())){
            expenseBillService.setStateValidated(lineBill.getIdExpenseBill());
        }
        return HttpStatus.OK;
    }

    public HttpStatus refuseLineBill(int lineBillId) throws LineBillException, ExpenseBillException {
        if (!this.lineBillRepository.existsById(lineBillId)) {
            throw new LineBillException("Impossible to update an inexisting lineBill", HttpStatus.CONFLICT);
        }
        LineBill lineBill = lineBillRepository.findById(lineBillId);
        lineBill.setState(LineBillSates.REFUSED);
        lineBillRepository.save(lineBill);

        return HttpStatus.OK;
    }
}
