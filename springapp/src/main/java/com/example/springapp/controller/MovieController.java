package com.example.springapp.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.*;
import com.example.springapp.model.User;
import com.example.springapp.model.Review;

import org.springframework.web.bind.annotation.*;
import com.example.springapp.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springapp.repository.MovieRepository;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.repository.ReviewRepository;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;


@Controller
@ResponseBody
public class MovieController {
  private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

  @GetMapping("/movie")
  public List<Movie> getResponseTestMovie() {
    return movieRepository.findAll(); 
  }
  @GetMapping("/review")
  public List<Review> getResponseTestReview() {
    return reviewRepository.findAll(); 
  }
  @PostMapping("/user/register")
  public ResponseEntity<String> registerUser(@RequestBody Map<String,Object> requestBody) {
    String uName = (String) requestBody.get("Username");
    String pWdd = (String) requestBody.get("Password");
    String role = (String) requestBody.get("role");
    return ResponseEntity.ok("added");
  } 
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestParam("email") String email,@RequestParam("password") String password) {
    User user = userRepository.findByEmail(email).get(0);
    log.info(email+" "+password);
    if (user.password.equals(password)) {
      return ResponseEntity.ok("ok");
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
  }

}