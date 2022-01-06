package com.example.demo.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.exception.LineBillException;
import com.example.demo.repository.LineBillRepository;
import com.example.demo.vo.LineBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LineBillService {
    @Autowired
    LineBillRepository lineBillRepository;

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
        //TODO check attributs non empty

        return this.lineBillRepository.save(lineBill);

    }

    public void checkTVA(LineBill lineBill) throws LineBillException {
        //check montant et tva
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


}
