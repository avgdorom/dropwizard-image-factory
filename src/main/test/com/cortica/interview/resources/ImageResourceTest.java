package com.cortica.interview.resources;

import com.cortica.interview.BaseTest;
import com.cortica.interview.core.services.ImageManipulationService;
import com.cortica.interview.core.services.ObjectMapperService;
import com.cortica.interview.db.ImageDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by avgdorom on 7/23/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageResourceTest extends BaseTest {

    @InjectMocks
    private ImageResource imageResource;

    @Mock
    private ImageManipulationService imageManipulationService;

    @Mock
    private ImageDao imageDao;

    @Mock
    private ObjectMapperService objectMapperService;

    @Test
    public void processImage() throws Exception {
        imageResource.processImage(IMAGE_URL, REQUEST_PARAMETERS);
        verify(imageManipulationService).processImage(eq(IMAGE_URL), eq(REQUEST_PARAMETERS));
    }

    @Test
    public void processDemoImages() throws Exception {
        imageResource.processDemoImages(REQUEST_PARAMETERS);
        verify(imageManipulationService).processDemoImages(eq(REQUEST_PARAMETERS));
        verify(imageDao).findAll();
    }

    @Test
    public void getAllImages() throws Exception {
        imageResource.getAllImages();
        verify(imageDao).findAll();
    }

    @Test
    public void getImage() throws Exception {
        imageResource.getImage(IMAGE_NAME);
        verify(imageManipulationService).getImage(eq(IMAGE_NAME));
    }
}