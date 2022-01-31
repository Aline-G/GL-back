package com.expensebills.back.service;

import com.expensebills.back.exception.AdvanceException;
import com.expensebills.back.exception.DateException;
import com.expensebills.back.exception.ExpenseBillException;
import com.expensebills.back.repository.AdvanceRepository;
import com.expensebills.back.vo.Advance;
import com.expensebills.back.vo.BillStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdvanceService {
    @Autowired
    AdvanceRepository advanceRepository;
    @Autowired
    ExpenseBillService expenseBillService;

    public Advance saveAdvance(Advance advance) throws AdvanceException {
        if (this.advanceRepository.existsById(advance.getId())) {
            throw new AdvanceException("Id advance already exist", HttpStatus.CONFLICT);
        }

        return this.advanceRepository.save(advance);
    }


    public List<Advance> getAdvanceList() {
        return StreamSupport.stream(this.advanceRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Advance askForValidation(int id) throws AdvanceException {
        if (!this.advanceRepository.existsById(id)) {
            throw new AdvanceException("This advance does not exists", HttpStatus.CONFLICT);
        }
        Advance advance = advanceRepository.findById(id);
        advance.setState(BillStates.WAITING);
        advanceRepository.save(advance);
        return advance;
    }

    public HttpStatus deleteAdvance(int id) throws AdvanceException {
        //Check existence of this id
        if(!advanceRepository.existsById(id)){
            throw new AdvanceException("Id doesn't exist", HttpStatus.BAD_REQUEST);
        }
        this.advanceRepository.delete(this.advanceRepository.findById(id));
        return HttpStatus.OK;
    }

    public Advance validation(int id) throws AdvanceException, ExpenseBillException, DateException {
        if (!(this.advanceRepository.existsById(id))) {
            throw new AdvanceException("Id advance does not exist", HttpStatus.CONFLICT);
        }
        if(this.advanceRepository.findById(id).getState() == BillStates.VALIDATED){
            throw new AdvanceException("this advance is already validated", HttpStatus.CONFLICT);
        }
        Advance advance = this.advanceRepository.findById(id);
        advance.setState(BillStates.VALIDATED);
        this.advanceRepository.save(advance);
        this.expenseBillService.addAdvanceToCurrentBill(advance);
        return advance;


    }
}
