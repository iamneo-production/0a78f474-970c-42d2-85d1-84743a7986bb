
package com.example.server.controllers;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.server.Exception.ResourceNotFoundException;
import com.example.server.models.Movie;
import com.example.server.models.MovieRepository;
import com.example.server.models.Review;
import com.example.server.models.ReviewRepository;
import com.example.server.models.User;
import com.example.server.models.UserRepository;

import java.time.LocalDate;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@CrossOrigin(origins =  {"${MY_APP_BASE_URL}"})
@Controller
@ResponseBody
public class ReviewController {

    private final MovieRepository movieRepository;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    Authentication authentication;

    @Autowired
    public ReviewController(MovieRepository movieRepository,
            UserRepository userRepository, ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    // retrives all the reviews for a movie
    @GetMapping("api/reviews/{movieId}")
    public ResponseEntity<List<Review>> getAllReviewsForMovie(@PathVariable(value = "movieId") String movieId) {
        Movie _movie = movieRepository.findById(movieId).orElse(null);
        List<Review> reviews = null;
        if (_movie != null) {
            reviews = _movie.getReviews();
            for (Review rev : reviews) {
                User user = userRepository.findById(rev.getUserId()).orElse(null);
                rev.setUsername(user.getUserName());
            }
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public String hello() {
        return "hello";
    }

    // update review by reviewId
    @Secured("ROLE_USER")
    @PutMapping("user/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable(value = "reviewId") String reviewId,
            @RequestBody Review reviewRequest) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Review _Review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + reviewId));
        User user = userRepository.findByUserName(userDetails.getUsername()).get(0);

        reviewRequest.setUsername(user.getUserName());
        reviewRequest.setUserId(user.getUserId());

        if (_Review != null) {
            _Review.setRating(reviewRequest.getRating());
            _Review.setReviewText(reviewRequest.getReviewText());
            _Review.setUsername(reviewRequest.getUsername());
            _Review.setEdited(true);
            _Review.setReviewDate(LocalDate.now());
        } else {
            System.out.println("null value returned*********");
        }
        return new ResponseEntity<>(reviewRepository.save(_Review), HttpStatus.OK);

    }

    // add review to a movie
    @PostMapping("user/reviews/{movieId}")
    public ResponseEntity<Review> createReview(@PathVariable(value = "movieId") String movieId,
            @RequestBody Review reviewRequest) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Review review = movieRepository.findById(movieId).map(movie -> {
            User user = userRepository.findByUserName(userDetails.getUsername()).get(0);
            reviewRequest.setUsername(user.getUserName());
            reviewRequest.setUserId(user.getUserId());

            reviewRequest.setMovie(movie);
            return reviewRepository.save(reviewRequest);

        }).orElseThrow(() -> new ResourceNotFoundException("not found movie with id" + movieId));

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    // retrives over all rating for a movie
    @GetMapping("api/reviews/rating/{movieId}")
    public ResponseEntity<String> retrieveRating(@PathVariable(value = "movieId") String movieId) {
        String rating = reviewRepository.rating(movieId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    // delete review by reviewId
    @DeleteMapping("user/reviews/{reviewId}")
    public ResponseEntity<HttpStatus> deleteReviewById(@PathVariable(value = "reviewId") String reviewId) {
        reviewRepository.deleteById(reviewId);
        System.out.println("review deleted");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("user/reviews/specific/")
    public ResponseEntity<List<Review>> getUserReviews() {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        List<Review> results = new ArrayList<>();
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserName(userDetails.getUsername()).get(0);

        for (Review rv : reviews) {
            User _user = userRepository.findById(rv.getUserId()).get();
            if (_user != null && _user.getUserId().equals(user.getUserId())) {
                rv.setMovieId(rv.getMovie().getMovieId());
                rv.setUsername(user.getUserName());
                results.add(rv);
            }
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}