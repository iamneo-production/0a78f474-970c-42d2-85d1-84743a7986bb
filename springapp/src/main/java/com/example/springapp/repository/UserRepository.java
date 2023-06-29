package com.example.springapp.repository;


import com.example.springapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User,String> {
    List<User> findByEmail(String emailString);
}
