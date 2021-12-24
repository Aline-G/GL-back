package com.example.demo.repository;


import com.example.demo.vo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByFirstname(String name);
}
