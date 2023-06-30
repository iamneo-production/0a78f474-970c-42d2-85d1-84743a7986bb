package com.example.springapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String rating;

    @Column(name = "review_note")
    private String reviewNote;

    private String source;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // Constructors, getters, and setters

    public Review() {
        // Default constructor
    }

    public Review(Long userId, String rating, String reviewNote, String source, Date date, Movie movie) {
        this.userId = userId;
        this.rating = rating;
        this.reviewNote = reviewNote;
        this.source = source;
        this.date = date;
        this.movie = movie;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
