package com.expensebills.back.repository;

import com.expensebills.back.vo.ExpenseBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseBillRepository extends CrudRepository<ExpenseBill, Integer> {
    @Override
    void delete(ExpenseBill expenseBill);

    ExpenseBill findById(int id);
}
