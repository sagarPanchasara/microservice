package com.infostretch.microservice.service;

import com.infostretch.microservice.domain.Movie;
import com.infostretch.microservice.repository.MovieRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    @NonNull
    private MovieRepository movieRepository;

    public Page<Movie> list(Pageable page) {
        return movieRepository.findAll(page);
    }

    public Optional<Movie> get(String id) {
        log.info("This is test logger for get service");
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(String id) {
        movieRepository.deleteById(id);
    }

}
