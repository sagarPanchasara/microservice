package com.infostretch.microservice.repository;

import com.infostretch.microservice.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
