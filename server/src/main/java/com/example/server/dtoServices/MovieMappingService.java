package com.example.server.dtoServices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.server.dto.MovieDto;
import com.example.server.models.Cast;
import com.example.server.models.CastRepository;
import com.example.server.models.Movie;
import com.example.server.models.ReviewRepository;

@Service
public class MovieMappingService {

    @Autowired
    private CastRepository castRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<MovieDto> getAllMoviesForCast(String castName) {
        return castRepository.findByCastNameContaining(castName)
                .stream()
                .map(this::convertDataIntoDTO)
                .collect(Collectors.toList());
    }

    private MovieDto convertDataIntoDTO(Cast cast) {

        MovieDto dto = new MovieDto();
        dto.setCastName(cast.getCastName());

        Movie movie = cast.getMovie();
        if (movie != null) {
            if (reviewRepository.rating(movie.getMovieId()) != null) {
                movie.setRating(reviewRepository.rating(movie.getMovieId()));
            }
        }
        dto.setRating(movie.getRating());
        dto.setMovieId(movie.getMovieId());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setMovieDesc(movie.getMovieDesc());
        dto.setTitle(movie.getTitle());

        return dto;

    }

}