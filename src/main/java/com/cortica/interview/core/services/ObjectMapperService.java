package com.cortica.interview.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.springframework.stereotype.Service;

/**
 * Created by avgdorom on 7/22/2016.
 */
@Service("objectMapperService")
public class ObjectMapperService {

    private final ObjectMapper objectMapper = Jackson.newObjectMapper();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
