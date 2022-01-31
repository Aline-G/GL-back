package com.example.demo.repository;

import com.example.demo.vo.Mission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MissionRepository extends CrudRepository<Mission, Integer> {
    @Override
    void delete(Mission mission);

    Mission findById(int id);
}
