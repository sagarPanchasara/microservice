package com.infostretch.subscription.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("subscriptions")
@Data
public class Subscription {

    private String id;
    private String userId;
    private String planName;
    private LocalDate subscribedOn;
    private LocalDate subscriptionExpiry;

}
