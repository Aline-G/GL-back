package com.example.demo.service;

import com.example.demo.exception.AdvanceException;
import com.example.demo.repository.AdvanceRepository;
import com.example.demo.vo.Advance;
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

    public Advance saveAdvance(Advance advance) throws AdvanceException {
        if (this.advanceRepository.existsById(advance.getId())) {
            throw new AdvanceException("Id advance already exist", HttpStatus.CONFLICT);
        }

        return this.advanceRepository.save(advance);
    }


    public List<Advance> getAdvanceList() {
        return StreamSupport.stream(this.advanceRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
