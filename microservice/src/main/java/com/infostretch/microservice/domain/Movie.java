package com.infostretch.microservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;

@Document("movies")
@Data
public class Movie {

    @Null(message = "id field is auto generated")
    @JsonProperty("id")
    private String id;

    @NotBlank(message = "name is required")
    @JsonProperty("name")
    private String name;

    @Min(value = 0, message = "ratings can not be less than zero")
    @Max(value = 10, message = "ratings can not be grater than zero")
    @JsonProperty("ratings")
    private int ratings;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/kolkata")
    @JsonProperty("release_date")
    private Date releaseDate;

}
