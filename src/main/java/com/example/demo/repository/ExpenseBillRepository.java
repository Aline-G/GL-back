package com.example.demo.repository;

import com.example.demo.vo.ExpenseBill;
import com.example.demo.vo.LineBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseBillRepository extends CrudRepository<ExpenseBill, Integer> {
    @Override
    void delete(ExpenseBill expenseBill);

    ExpenseBill findById(int id);
}
