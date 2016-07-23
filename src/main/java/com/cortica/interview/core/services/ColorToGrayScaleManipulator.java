package com.cortica.interview.core.services;

import org.springframework.stereotype.Service;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.util.Map;

/**
 * Created by avgdorom on 7/20/2016.
 */
@Service("colorToGrayScaleManipulator")
public class ColorToGrayScaleManipulator implements ImageManipulator {

    @Override
    public BufferedImage manipulate(BufferedImage image, Map<String, Object> manipulationContext) {
        BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return op.filter(image, null);
    }

    @Override
    public String getName() {
        return "gray";
    }
}
