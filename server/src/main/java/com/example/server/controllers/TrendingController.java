package com.example.server.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.server.models.Movie;
import com.example.server.models.MovieRepository;
import com.example.server.models.ReviewRepository;
import com.example.server.models.Trending;
import com.example.server.models.TrendingRepository;

@CrossOrigin(origins = "https://8081-fbecdbbdadebcceabbaeaeaadbdbabf.project.examly.io")
@Controller
@ResponseBody
public class TrendingController {

    private final TrendingRepository trendingRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public TrendingController(TrendingRepository trendingRepository, MovieRepository movieRepository,
            ReviewRepository reviewRepository) {
        this.trendingRepository = trendingRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;

    }

    // add a movie to trending

    @GetMapping("/admin/trending/{movieId}")
    public ResponseEntity<String> addTrending(@PathVariable String movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null) {
            Trending trending = new Trending();
            trending.setMovie(movie);
            trendingRepository.save(trending);
            // System.out.println("hii");
            return ResponseEntity.ok("Movie added to trending");
        }
        return ResponseEntity.notFound().build();

    }

    // is a movie trending
    @GetMapping("/api/trending/istrending/{movieId}")
    public boolean isTrending(@PathVariable String movieId) {

        Optional<Trending> optionalTrending = trendingRepository.findByMovieMovieId(movieId);
        if (optionalTrending.isPresent()) {
            Trending trending = optionalTrending.get();
            return trending != null;
        } else {
            return false;
        }

    }

    // list of trending movies
    @GetMapping("/api/trending")
    public ResponseEntity<List<Movie>> getAllTrendingMovies() {
        List<Trending> trendingMovies = trendingRepository.findAll();
        List<Movie> movies = new ArrayList<>();

        for (Trending trending : trendingMovies) {
            Movie mov = trending.getMovie();
            if (mov != null) {
                if (reviewRepository.rating(mov.getMovieId()) != null) {
                    mov.setRating(reviewRepository.rating(mov.getMovieId()));
                }
            }
            movies.add(trending.getMovie());
        }

        return ResponseEntity.ok(movies);
    }

    // delete a movie from trending
    @DeleteMapping("/admin/trending/{movieId}")
    public ResponseEntity<String> deleteMovieFromTrending(@PathVariable String movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null) {
            Trending trending = movie.getTrending();
            movie.setTrending(null);

            trending.setMovie(null);
            movieRepository.save(movie); // Update the movie entity to remove the association

            trendingRepository.delete(trending); // Delete the Trending entity
            return ResponseEntity.ok("Movie removed from trending");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}