package com.cortica.interview.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by avgdorom on 7/20/2016.
 */
public class BasicHealthCheck extends HealthCheck {

    public BasicHealthCheck() {

    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
