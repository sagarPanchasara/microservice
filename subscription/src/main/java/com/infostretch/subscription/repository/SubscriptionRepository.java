package com.infostretch.subscription.repository;

import com.infostretch.subscription.domain.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    Optional<Subscription> findByUserIdAndSubscriptionExpiryAfter(String userId, LocalDate now);

}
