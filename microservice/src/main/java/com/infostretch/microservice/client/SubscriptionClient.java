package com.infostretch.microservice.client;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "subscription-service", fallbackFactory = SubscriptionClient.SubscriptionClientFallbackFactory.class)
public interface SubscriptionClient {

    @GetMapping("/subscription/isValid/{userId}")
    boolean isValidSubscriber(@PathVariable String userId);

    @Slf4j
    @Component
    class SubscriptionClientFallbackFactory implements FallbackFactory<SubscriptionClient> {

        @Override
        public SubscriptionClient create(Throwable throwable) {
            return new SubscriptionClient() {
                @Override
                public boolean isValidSubscriber(String userId) {
                    log.error("isValidSubscriber: error {}", throwable.getMessage());
                    return false;
                }
            };
        }
    }

}

