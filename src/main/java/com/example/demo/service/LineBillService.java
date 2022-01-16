package com.example.demo.service;

import com.example.demo.exception.ExpenseBillException;
import com.example.demo.exception.LineBillException;
import com.example.demo.repository.LineBillRepository;
import com.example.demo.vo.LineBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LineBillService {
    @Autowired
    LineBillRepository lineBillRepository;
    @Autowired
    ExpenseBillService expenseBillService;

    public LineBill saveLineBill(LineBill lineBill) throws LineBillException {

        // check the existence of TVA data
        if(lineBill.getTva() == null && lineBill.getTvaPercent() == null){
            throw new LineBillException("Informations about TVA are missing", HttpStatus.BAD_REQUEST);
        }

        //TVA Calcul
        if(lineBill.getTva() == null || lineBill.getTvaPercent() == null){
            lineBill = calculTVA(lineBill);
        }

        //Check TVA
        checkTVA(lineBill);

        //Check the id of the line
        if (this.lineBillRepository.existsById(lineBill.getId())) {
            throw new LineBillException("Id line already exist", HttpStatus.CONFLICT);
        }

        return this.lineBillRepository.save(lineBill);

    }

    public void checkTVA(LineBill lineBill) throws LineBillException {
        //check montant et tva
        if( lineBill.getAmount() <= lineBill.getTva()){
            throw new LineBillException("Amount of TVA too high",HttpStatus.BAD_REQUEST);
        }
        //Check TVA coherence
        float tvaCalcul = lineBill.getAmount()*lineBill.getTvaPercent()/100;
        if( tvaCalcul != lineBill.getTva()){
            throw new LineBillException("Amount of TVA not coherent",HttpStatus.BAD_REQUEST);
        }
    }


    public LineBill calculTVA(LineBill lineBill){
        if(lineBill.getTva()==null){
            lineBill.setTva(lineBill.getAmount()*lineBill.getTvaPercent()/100);
        }else{
            lineBill.setTvaPercent(lineBill.getTva()*100/lineBill.getAmount());
        }
        return lineBill;
    }

    public HttpStatus deleteLineBill(int id) throws LineBillException {
        //Check existence of this id
        if(!lineBillRepository.existsById(id)){
            throw new LineBillException("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        this.lineBillRepository.delete(this.lineBillRepository.findById(id));
        return HttpStatus.OK;
    }

    public LineBill getLine(int id){
        return lineBillRepository.findById(id);
    }

    //TODO update
    public LineBill updateLine(int id) throws LineBillException {
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
        if(lineBill.getTva() == null && lineBill.getTvaPercent() == null){
            throw new LineBillException("Informations about TVA are missing", HttpStatus.BAD_REQUEST);
        }

        //TVA Calcul
        if(lineBill.getTva() == null || lineBill.getTvaPercent() == null){
            lineBill = calculTVA(lineBill);
        }

        //Check TVA
        checkTVA(lineBill);
        return this.lineBillRepository.save(lineBill);
    }

    public List<LineBill> getLineBillList() {
        return StreamSupport.stream(this.lineBillRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    // TODO verifier que c'est bien un manager qui fait la demande
    public HttpStatus validLineBill(int lineBillId) throws LineBillException, ExpenseBillException {
        if (!this.lineBillRepository.existsById(lineBillId)) {
            throw new LineBillException("Impossible to update an inexisting lineBill", HttpStatus.CONFLICT);
        }
        LineBill lineBill = lineBillRepository.findById(lineBillId);
        lineBill.setValidated(true);
        lineBillRepository.save(lineBill);

        if(expenseBillService.allValidated(lineBill.getIdExpenseBill())){
            expenseBillService.setStateValidated(lineBill.getIdExpenseBill());
        }
        return HttpStatus.OK;
    }
}
