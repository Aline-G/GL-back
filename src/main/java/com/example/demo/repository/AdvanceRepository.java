package com.example.demo.repository;


import com.example.demo.vo.Advance;
import com.example.demo.vo.ExpenseBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface AdvanceRepository extends CrudRepository<Advance, Integer> {
    @Override
    void delete(Advance advance);

    Advance findById(int id);
    Advance findByDate(LocalDate date);

    boolean existsByDate(LocalDate date);

}
