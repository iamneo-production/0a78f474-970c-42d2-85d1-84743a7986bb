
package com.example.server.controllers;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.server.Exception.ResourceNotFoundException;
import com.example.server.dto.MovieGenreDto;
import com.example.server.dtoServices.MovieGenreMappingService;
import com.example.server.models.Cast;
import com.example.server.models.Genre;
import com.example.server.models.Movie;
import com.example.server.models.MovieRepository;
import com.example.server.models.ReviewRepository;

import org.springframework.http.HttpStatus;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@ResponseBody
public class MovieController {

    private final MovieRepository movieRepository;
    private final MovieGenreMappingService movieGenreMappingService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository, ReviewRepository reviewRepository,
            MovieGenreMappingService movieGenreMappingService) {
        this.movieRepository = movieRepository;
        this.movieGenreMappingService = movieGenreMappingService;
        this.reviewRepository = reviewRepository;
    }

    // add movie
    @PostMapping("admin/movies")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getCasts() != null) {
            for (Cast cast : movie.getCasts())
                if (cast.getCastName() != "") {
                    movie.addCast(cast);
                }
        }

        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres())
                if (genre.getCategory() != "") {
                    movie.addGenre(genre);
                }
        }

        Movie _movie = movieRepository.save(movie);
        return new ResponseEntity<>(_movie, HttpStatus.CREATED);

    }

    // retrives all the movie
    @GetMapping("/api/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Movie mov : movies) {
            if (mov != null) {
                if (reviewRepository.rating(mov.getMovieId()) != null) {
                    mov.setRating(reviewRepository.rating(mov.getMovieId()));
                }
            }
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // retrives movie that has rating greater than 4
    @GetMapping("api/movies/recommendation")
    public ResponseEntity<List<Movie>> getMoviesForRecommendation() {
        List<Movie> movies = movieRepository.findAll();
        List<Movie> rMovies = new ArrayList<>();
        for (Movie mov : movies) {
            if (mov != null) {
                if (reviewRepository.rating(mov.getMovieId()) != null) {
                    mov.setRating(reviewRepository.rating(mov.getMovieId()));
                }
                if (mov.getRating() != null && Double.parseDouble(mov.getRating()) >= 4) {
                    rMovies.add(mov);
                }
            }
        }
        return new ResponseEntity<>(rMovies, HttpStatus.OK);

    }

    // get movie detail by id
    @GetMapping("api/movies/{movieId}")
    public ResponseEntity<Movie> getMoviesById(@PathVariable String movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        movie.setRating(reviewRepository.rating(movieId));
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    // update movie detail by id
    @PutMapping("admin/movies/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("movieId") String movieId, @RequestBody Movie movie) {
        Movie _movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("not found movie with id" + movieId));

        if (movie.getCasts() != null) {
            for (Cast cast : movie.getCasts())
                if (cast.getCastName() != "") {
                    _movie.addCast(cast);
                }
        }

        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres())
                if (genre.getCategory() != "") {
                    _movie.addGenre(genre);
                }
        }
        // System.out.println(movie.getGenres());
        _movie.setTitle(movie.getTitle());
        _movie.setRuntime(movie.getRuntime());
        _movie.setReleaseDate(movie.getReleaseDate());
        _movie.setProducer(movie.getProducer());
        _movie.setMovieDesc(movie.getMovieDesc());
        _movie.setMotionPictureRating(movie.getMotionPictureRating());
        _movie.setLanguage(movie.getLanguage());
        _movie.setDirector(movie.getDirector());
        _movie.setCollection(movie.getCollection());
        _movie.setPosterUrl(movie.getPosterUrl());
        _movie.setTrailerId(movie.getTrailerId());
        return new ResponseEntity<>(movieRepository.save(_movie), HttpStatus.OK);

    }

    // deletes all the movies
    @DeleteMapping("admin/movies")
    public ResponseEntity<HttpStatus> deleteAllMovies() {
        movieRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // delete movie by movieId
    @DeleteMapping("admin/movies/{movieId}")
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("movieId") String movieId) {
        Movie _movie = movieRepository.findById(movieId).orElse(null);
        _movie.getGenres().clear();
        movieRepository.deleteById(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // search movie by title
    @GetMapping("api/movies/search/title")
    public ResponseEntity<List<Movie>> getMovieByTitle(@RequestParam("title") String title) {
        title = title.toLowerCase();
        List<Movie> movies = movieRepository.findMoviesByTitleContainingSequence(title);
        for (Movie mov : movies) {
            if (mov != null) {
                if (reviewRepository.rating(mov.getMovieId()) != null) {
                    mov.setRating(reviewRepository.rating(mov.getMovieId()));
                }
            }

        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // search movie by genre
    @GetMapping("/api/movies/search/genre")
    public ResponseEntity<List<MovieGenreDto>> getmoviesForCast(@RequestParam("category") String category) {
        List<MovieGenreDto> movieGnereDtoList = movieGenreMappingService.getAllMoviesForGenre(category);
        return new ResponseEntity<>(movieGnereDtoList, HttpStatus.OK);
    }

    /*
     * //to delete genre of a movie
     * 
     * @PutMapping("api/movies/{genreId}/{movieId}")
     * public ResponseEntity<String> removeGenre(@PathVariable String
     * movieId, @PathVariable String genreId) {
     * 
     * Genre genreToRemove = null;
     * Movie _movie = movieRepository.findById(movieId).orElse(null);
     * if (_movie != null) {
     * 
     * for (Genre genre : _movie.getGenres()) {
     * if (genre.getGenreId().equals(genreId)) {
     * genreToRemove = genre;
     * break;
     * }
     * }
     * }
     * if (genreToRemove == null) {
     * return ResponseEntity.notFound().build();
     * }
     * 
     * // Remove the genre from the movie's genres
     * _movie.removeGenre(genreToRemove);
     * 
     * // Save the updated movie
     * movieRepository.save(_movie);
     * 
     * return new ResponseEntity<String>("genre removed", HttpStatus.OK);
     * }
     */
    /*
     * {19421712-77f9-446f-813c-7a85338bd8b0
     * "title":"mozhi",
     * "director":"Radha Mohan",
     * "producer":"Prakash Raj",
     * "motionPictureRating":"PG",
     * "movieDesc":"A musician's love towards a stubborn deaf woman makes her eventually realize that there is more to her life than she previously thought."
     * ,
     * "runtime":"2h 35m",
     * "collection":"2.22",
     * "genre":"love,romance",
     * "language":"Tamil",
     * "releaseYear":"2007"
     * }
     * {
     * "title":"jodha akbar",
     * "director":"Ashutosh Gowariker",
     * "producer":"Prakash Raj",
     * "motionPictureRating":"PG",
     * "movieDesc":"A sixteenth century love story about a marriage of alliance that gave birth to true love between a great Mughal emperor, Akbar, and a Rajput princess, Jodha."
     * ,
     * "runtime":"3h 10m",
     * "collection":"2.22",
     * "genres":[{"category":"love"},{"category":"romance"}],
     * "language":"hindi",
     * "releaseYear":"2008"
     * }
     */

}

