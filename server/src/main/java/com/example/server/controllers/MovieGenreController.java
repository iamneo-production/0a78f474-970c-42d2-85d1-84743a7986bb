package com.example.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.server.models.Genre;
import com.example.server.models.GenreRepository;

@CrossOrigin(origins =  {"${MY_APP_BASE_URL}"})
@Controller
@ResponseBody
public class MovieGenreController {

    private final GenreRepository genreRepository;

    @Autowired
    public MovieGenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // get genre by category
    @GetMapping("api/genre/category")
    public ResponseEntity<List<Genre>> findBygenre(@RequestParam("category") String category) {
        List<Genre> _genre = genreRepository.findByCategoryContaining(category);
        return new ResponseEntity<>(_genre, HttpStatus.OK);
    }

    @GetMapping("api/genre")
    public ResponseEntity<List<Genre>> findAllGenre() {
        List<Genre> genres = genreRepository.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);

    }

    // delete genre by genreId
    @DeleteMapping("admin/genre/{genreId}")
    public ResponseEntity<HttpStatus> deleteGenreById(@PathVariable("genreId") String genreId) {
        genreRepository.deleteById(genreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}