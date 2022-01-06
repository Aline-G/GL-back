package com.example.demo.service;

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
        //Check the id of the line
        if (this.lineBillRepository.existsById(lineBill.getId())) {
            throw new LineBillException("Id line already exist", HttpStatus.CONFLICT);
        }
        //TODO check attributs non empty

        return this.lineBillRepository.save(lineBill);

    }


}
