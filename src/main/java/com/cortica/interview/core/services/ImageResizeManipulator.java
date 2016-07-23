package com.cortica.interview.core.services;

import com.cortica.interview.api.Constants;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by avgdorom on 7/20/2016.
 */
@Service("imageResizeManipulator")
public class ImageResizeManipulator implements ImageManipulator {

    public BufferedImage manipulate(BufferedImage image, Map<String, Object> manipulationContext) {
        int width = manipulationContext.get("w") == null ? Constants.DEFAULT_WIDTH : Integer.parseInt(manipulationContext.get("w").toString());
        int height = manipulationContext.get("h") == null ? Constants.DEFAULT_HEIGHT : Integer.parseInt(manipulationContext.get("h").toString());
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return resizedImage;
    }

    @Override
    public String getName() {
        return "resize";
    }
}
