package com.expensebills.back.repository;

import com.expensebills.back.vo.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {
    @Override
    void delete(Team team);

    Team findById(int id);
}
