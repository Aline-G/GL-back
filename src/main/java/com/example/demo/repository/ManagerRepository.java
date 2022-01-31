package com.example.demo.repository;


import com.example.demo.vo.Manager;
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
