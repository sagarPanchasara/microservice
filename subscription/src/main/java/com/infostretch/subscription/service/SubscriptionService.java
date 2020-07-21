package com.infostretch.subscription.service;

import com.infostretch.subscription.domain.Subscription;
import com.infostretch.subscription.repository.SubscriptionRepository;
import com.infostretch.subscription.util.PlanHelper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    @NonNull
    private SubscriptionRepository subscriptionRepository;

    public void subscribe(String userId, int planId) {
        Subscription subscription = new Subscription();
        subscription.setSubscribedOn(LocalDate.now());
        PlanHelper.Plan plan = PlanHelper.getById(planId);
        subscription.setSubscriptionExpiry(LocalDate.now().plusDays(plan.getValidity()));
        subscription.setPlanName(plan.getName());
        subscription.setUserId(userId);
        subscriptionRepository.save(subscription);
    }

    public boolean isValidSubscriber(String userId) {
        return subscriptionRepository.findByUserIdAndSubscriptionExpiryAfter(userId, LocalDate.now()).isPresent();
    }
}
