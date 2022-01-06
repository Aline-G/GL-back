package com.example.demo.repository;

import com.example.demo.vo.LineBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineBillRepository extends CrudRepository<LineBill, Integer> {
    @Override
    void delete(LineBill lineBill);

    LineBill findById(int id);

}
