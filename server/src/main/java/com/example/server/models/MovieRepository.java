package com.example.server.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

  List<Movie> findByTitle(String title);

  @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE %:sequence%")
  List<Movie> findMoviesByTitleContainingSequence(String sequence);

}