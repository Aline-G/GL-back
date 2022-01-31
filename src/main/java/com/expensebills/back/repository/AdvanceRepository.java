package com.expensebills.back.repository;


import com.expensebills.back.vo.Advance;
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
