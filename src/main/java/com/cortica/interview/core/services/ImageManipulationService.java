package com.cortica.interview.core.services;

import com.cortica.interview.api.Constants;
import com.cortica.interview.api.ImageStatus;
import com.cortica.interview.db.ImageDao;
import com.cortica.interview.db.ImageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.dropwizard.jackson.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by avgdorom on 7/22/2016.
 */
@Service("imageManipulationService")
public class ImageManipulationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageManipulationService.class);

    private MessageDigest md5Digest;

    @Autowired
    private List<ImageManipulator> imageManipulators;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ObjectMapperService objectMapperService;

    public ImageManipulationService() {
        try {
            md5Digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalStateException();
        }
    }

    public void processImage(String imageUrl, String requestParameters) {
        ImageStatus status = ImageStatus.SUCCESS;
        ImageEntity imageEntity = createImageEntity(imageUrl, requestParameters);
        try {
            Map<String, Map<String, Object>> manipulationContexts = parseRequestParameters(requestParameters);
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            BufferedImage image = ImageIO.read(inputStream);

            List<ImageManipulator> relevantImageManipulators = imageManipulators.stream().
                    filter(imageManipulator -> manipulationContexts.keySet().contains(imageManipulator.getName())).
                    collect(Collectors.toList());
            for (ImageManipulator manipulator : relevantImageManipulators) {
                image = manipulator.manipulate(image, manipulationContexts.get(manipulator.getName()));
            }

            imageEntity.setWidth(image.getWidth());
            imageEntity.setHeight(image.getHeight());
            ImageIO.write(image, "jpg", new File(getResultImageAbsolutePath(getCurrentWorkingDirectory(), getResultImageFileName(imageUrl))));
            inputStream.close();
        } catch (Exception e) {
            LOGGER.warn(String.format("Error while downloading image [%s]. Message: %s", imageUrl, e.getMessage()));
            status = ImageStatus.FAILED;
        } finally {
            imageEntity.setStatus(status);
            imageDao.persist(imageEntity);
        }
    }

    private Map<String, Map<String, Object>> parseRequestParameters(String requestParameters) throws java.io.IOException {
        ObjectMapper objectMapper = objectMapperService.getObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, HashMap.class);
        return objectMapper.readValue(requestParameters, mapType);
    }

    private ImageEntity createImageEntity(String imageUrl, String requestParameters) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setDate(new Date().toString());
        imageEntity.setName(getResultImageFileName(imageUrl));
        imageEntity.setLocation(getCurrentWorkingDirectory());
        imageEntity.setTransformations(requestParameters);
        imageEntity.setUrl(imageUrl);
        imageEntity.setMd5(new String(md5Digest.digest(imageUrl.getBytes())));
        return imageEntity;
    }

    private String getCurrentWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    public void processDemoImages(String requestParameters) {
        URL resource = getClass().getClassLoader().getResource(Constants.ASSETS_INPUT_IMAGES_TXT);
        if (resource == null) {
            LOGGER.error(String.format("Could not find resource file [%s]", Constants.ASSETS_INPUT_IMAGES_TXT));
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(
                new File(resource.getFile())))) {
            String line;
            while ((line = br.readLine()) != null) {
                processImage(line, requestParameters);
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Error encountered! The demo failed!! :-( Message: %s", e.getMessage()));
        }
    }

    public byte[] getImage(String imageName) {
        try {
            BufferedImage image = ImageIO.read(new File(getResultImageAbsolutePath(getCurrentWorkingDirectory(), imageName)));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            LOGGER.error(String.format("Could not retrieve image [%s]", imageName));
            return new byte[0];
        }
    }

    private String getResultImageFileName(String imageUrl) {
        String[] imageUrlParts = imageUrl.split("/");
        return imageUrlParts[imageUrlParts.length - 1];
    }

    private String getResultImageAbsolutePath(String destinationDirectory, String imageFileName) {
        if (StringUtils.isNotBlank(destinationDirectory) && !destinationDirectory.endsWith("/")) {
            destinationDirectory += "/";
        }
        return destinationDirectory + imageFileName;
    }
}
