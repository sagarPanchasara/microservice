package com.infostretch.microservice.controller;

import com.infostretch.microservice.aspect.PremiumContent;
import com.infostretch.microservice.domain.Movie;
import com.infostretch.microservice.service.MovieService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    @NonNull
    private MovieService movieService;

    @GetMapping("/")
    public ResponseEntity<?> list(Pageable page, @RequestHeader String userId) {
        log.info("This is test logger for list controller");
        log.info("Found UserId: " + userId);
        return ResponseEntity.ok(movieService.list(page));
    }

    @GetMapping("/{id}")
    @PremiumContent
    public ResponseEntity<Movie> get(@PathVariable String id, @RequestHeader String userId) {
        log.info("Found UserId: " + userId);
        Optional<Movie> optional = movieService.get(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody @Valid Movie movie) {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid Movie movie) {
        movie.setId(id);
        return ResponseEntity.ok(movieService.save(movie));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        movieService.delete(id);
    }

}
