package com.example.demo.service;

import com.example.demo.exception.DateException;
import com.example.demo.exception.ExpenseBillException;
import com.example.demo.exception.LineBillException;
import com.example.demo.repository.ExpenseBillRepository;
import com.example.demo.vo.ExpenseBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ExpenseBillService {
    @Autowired
    ExpenseBillRepository expenseBillRepository;

    @Autowired
    DateService dateService;

    public ExpenseBill saveExpenseBill(ExpenseBill expenseBill) throws ExpenseBillException, DateException {

        dateService.isCoherent(expenseBill.getDate());

        if (this.expenseBillRepository.existsById(expenseBill.getId())) {
            throw new ExpenseBillException("Id of the bill already exists", HttpStatus.CONFLICT);
        }

        return this.expenseBillRepository.save(expenseBill);
    }


}
