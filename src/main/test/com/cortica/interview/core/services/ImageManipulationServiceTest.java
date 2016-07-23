package com.cortica.interview.core.services;

import com.cortica.interview.BaseTest;
import com.cortica.interview.api.ImageStatus;
import com.cortica.interview.db.ImageDao;
import com.cortica.interview.db.ImageEntity;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.*;

/**
 * Created by avgdorom on 7/23/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageManipulationServiceTest extends BaseTest {

    private static final String MALFORMED_REQUEST_PARAMETERS = "{resize:w\":100,\"h\":100}}";

    private static final String MANIPULATOR_NAME = "resize";

    @InjectMocks
    private ImageManipulationService imageManipulationService;

    @Spy
    private List<ImageManipulator> imageManipulators = new ArrayList<>(1);

    @Mock
    private ImageDao imageDao;

    @Mock
    private ObjectMapperService objectMapperService;

    @Mock
    private ImageManipulator imageManipulator;

    @Captor
    private ArgumentCaptor<ImageEntity> imageEntityArgumentCaptor;

    private BufferedImage image = new BufferedImage(DEFAULT_DIMENSION_SIZE, DEFAULT_DIMENSION_SIZE, BufferedImage.TYPE_INT_RGB);

    @Before
    public void init() {
        when(objectMapperService.getObjectMapper()).thenReturn(Jackson.newObjectMapper());
        when(imageManipulator.getName()).thenReturn(MANIPULATOR_NAME);
        when(imageManipulator.manipulate(any(BufferedImage.class), anyMap())).thenReturn(image);
        imageManipulators.add(imageManipulator);
    }

    @Test
    public void processImage() throws Exception {
        testCustomProcessImage(REQUEST_PARAMETERS, ImageStatus.SUCCESS, true);
    }

    @Test
    public void processImageInvalidRequestParameters() throws Exception {
        testCustomProcessImage(MALFORMED_REQUEST_PARAMETERS, ImageStatus.FAILED, false);
    }

    private void testCustomProcessImage(String requestParameters, ImageStatus status, boolean verifyManipulator) {
        imageManipulationService.processImage(IMAGE_URL, requestParameters);
        verify(imageDao).persist(imageEntityArgumentCaptor.capture());
        ImageEntity imageEntity = imageEntityArgumentCaptor.getValue();
        assertThat(imageEntity.getName(), equalTo(IMAGE_NAME));
        assertThat(imageEntity.getStatus(), equalTo(status));
        if (verifyManipulator) {
            verify(imageManipulator).manipulate(any(BufferedImage.class), anyMap());
            assertThat(imageEntity.getWidth(), equalTo(DEFAULT_DIMENSION_SIZE));
            assertThat(imageEntity.getHeight(), equalTo(DEFAULT_DIMENSION_SIZE));
        }
    }

    @Test
    public void processDemoImages() throws Exception {
        imageManipulationService.processDemoImages(REQUEST_PARAMETERS);
        verify(imageManipulator, times(4)).manipulate(any(BufferedImage.class), anyMap());
    }

    @Test
    public void getImage() throws Exception {
        imageManipulationService.processImage(IMAGE_URL, REQUEST_PARAMETERS);
        byte[] imageData = imageManipulationService.getImage(IMAGE_NAME);
        assertThat(imageData.length, equalTo(823));
    }

    @Test
    public void getImageThatDoesNotExist() throws Exception {
        byte[] imageData = imageManipulationService.getImage(IMAGE_NAME);
        assertThat(imageData, equalTo(new byte[0]));
    }
}