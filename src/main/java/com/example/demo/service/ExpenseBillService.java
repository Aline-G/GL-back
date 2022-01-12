package com.example.demo.service;

import com.example.demo.exception.DateException;
import com.example.demo.exception.ExpenseBillException;
import com.example.demo.repository.ExpenseBillRepository;
import com.example.demo.vo.BillStates;
import com.example.demo.vo.ExpenseBill;
import com.example.demo.vo.LineBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public HttpStatus deleteExpenseBill(int id) throws ExpenseBillException {
        //Check existence of this id
        if(!expenseBillRepository.existsById(id)){
            throw new ExpenseBillException("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        this.expenseBillRepository.delete(this.expenseBillRepository.findById(id));
        return HttpStatus.OK;
    }

    public void addLineBill(LineBill lineBill, int idExpenseBill) throws ExpenseBillException {
        if(!expenseBillRepository.existsById(idExpenseBill)){
            throw new ExpenseBillException("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }

        ExpenseBill expenseBill = expenseBillRepository.findById(idExpenseBill);
        List<LineBill> list = expenseBill.getListLineBill();
        list.add(lineBill);
        expenseBill.setListLineBill(list);
        expenseBill.setAmount(expenseBill.getAmount()+lineBill.getAmount());
        expenseBillRepository.save(expenseBill);
    }

    public List<ExpenseBill> getExpenseBillList() {
        return StreamSupport.stream(this.expenseBillRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }


    public void existsById(int idExpenseBill) throws ExpenseBillException {
        if(!this.expenseBillRepository.existsById(idExpenseBill)){
            throw new ExpenseBillException("This Expense Bill does not exists",HttpStatus.BAD_REQUEST);
        }
    }

    // TODO check aussi que les avances sont valide
    public boolean allValidated(int expenseBillId) throws ExpenseBillException {
        existsById(expenseBillId);
        ExpenseBill expenseBill = expenseBillRepository.findById(expenseBillId);
        boolean res = true;
        for(LineBill lineBill : expenseBill.getListLineBill()){
            if(!lineBill.isValidated()){
                res = false;
                break;
            }
        }
        return res;
    }

    public void setStateValidated(int expenseBillId){
        ExpenseBill expenseBill = expenseBillRepository.findById(expenseBillId);
        expenseBill.setState(BillStates.VALIDATED);
        expenseBillRepository.save(expenseBill);
    }

    //TODO penser a faire une fonction qui gere le moment ou le colaborateur demande a valider sa note

}