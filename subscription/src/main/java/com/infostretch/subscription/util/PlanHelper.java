package com.infostretch.subscription.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

public class PlanHelper {

    @Data
    @AllArgsConstructor
    public static class Plan {
        private int id;
        private String name;
        private long validity; // in days

    }

    public static final List<Plan> PLAN_LIST = Arrays.asList(
            new Plan(1, "Plan A", 1),
            new Plan(2, "Plan B", 2),
            new Plan(3, "Plan C", 3)
    );

    public static Plan getById(int id) {
        for (Plan plan : PLAN_LIST) {
            if (plan.getId() == id) return plan;
        }
        throw new IllegalArgumentException();
    }

}
