package com.expensebills.back.repository;

import com.expensebills.back.vo.Mission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MissionRepository extends CrudRepository<Mission, Integer> {
    @Override
    void delete(Mission mission);

    Mission findById(int id);
}
