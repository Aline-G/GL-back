package com.expensebills.back.repository;

import com.expensebills.back.vo.LineBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface LineBillRepository extends CrudRepository<LineBill, Integer> {
    @Override
    void delete(LineBill lineBill);

    LineBill findById(int id);

    ArrayList<LineBill> findAllByIdExpenseBill(int id);

}
