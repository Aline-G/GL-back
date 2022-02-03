package com.expensebills.back.repository;


import com.expensebills.back.vo.ActualUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActualUserRepository extends CrudRepository<ActualUser, Integer> {
    @Override
    void delete(ActualUser currentUser);

    @Override
    void deleteAll();
}
