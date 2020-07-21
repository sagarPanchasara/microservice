package com.infostretch.subscription.controller;

import com.infostretch.subscription.service.SubscriptionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    @NonNull
    private SubscriptionService subscriptionService;

    @GetMapping("/subscribe")
    public void subscribe(@RequestParam String userId, @RequestParam int planId) {
        subscriptionService.subscribe(userId, planId);
    }

    @GetMapping("/isValid/{userId}")
    public boolean isValidSubscriber(@PathVariable String userId) {
        return subscriptionService.isValidSubscriber(userId);
    }

}
