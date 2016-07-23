package com.cortica.interview;

import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by avgdorom on 7/23/2016.
 */
public class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    protected static final int DEFAULT_DIMENSION_SIZE = 100;

    protected static final String IMAGE_NAME = "nba-logo-on-wood.jpg";

    protected static final String IMAGE_URL = "http://www.logodesignlove.com/wp-content/uploads/2011/04/" + IMAGE_NAME;

    protected static final String REQUEST_PARAMETERS = "{\"resize\":{\"w\":" + DEFAULT_DIMENSION_SIZE + ",\"h\":" + DEFAULT_DIMENSION_SIZE + "}}";

    @After
    public void cleanUp() {
        File currentDirectory = new File(System.getProperty("user.dir"));
        for (File f : currentDirectory.listFiles()) {
            if (f.getName().endsWith(".jpg") && f.delete()) {
                LOGGER.info(String.format("Deleted file [%s] after tests ran", f.getName()));
            }
        }
    }
}
