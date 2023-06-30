package com.example.springapp.repository;


import com.example.springapp.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findAll();
  
}
