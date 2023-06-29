package com.example.springapp.repository;


import com.example.springapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, String> {

  List<Movie> findByTitle(String title);
  List<Movie> findAll();
  Page<Movie> findAll(Pageable pageable);
}