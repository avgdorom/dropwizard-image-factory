package com.cortica.interview.core.services;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by avgdorom on 7/22/2016.
 */
public interface ImageManipulator {

    BufferedImage manipulate(BufferedImage image, Map<String, Object> manipulationContext);

    String getName();
}
