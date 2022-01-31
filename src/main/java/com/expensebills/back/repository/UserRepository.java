package com.expensebills.back.repository;

import com.expensebills.back.vo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByFirstname(String name);

    User findByMail(String mail);

    User findById(int id);

    @Override
    void delete(User user);

}
