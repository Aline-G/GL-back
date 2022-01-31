package com.expensebills.back.repository;


import com.expensebills.back.vo.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    Manager findByFirstname(String name);

    Manager findByMail(String mail);
    Manager findById(int id);

    @Override
    void delete(Manager manager);


}
