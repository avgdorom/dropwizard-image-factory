package com.cortica.interview.resources;

import com.cortica.interview.core.services.ImageManipulationService;
import com.cortica.interview.core.services.ObjectMapperService;
import com.cortica.interview.db.ImageDao;
import com.cortica.interview.views.ImagesView;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by avgdorom on 7/20/2016.
 */
@Service
@Path("/image")
public class ImageResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResource.class);

    @Autowired
    private ImageManipulationService imageManipulationService;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ObjectMapperService objectMapperService;

    /**
     * Entry point for requesting to process an image from the internet.
     * Better be a POST request, but for the sake of simplicity here I left it as GET.
     *
     * @param imageUrl
     * @param requestParameters
     */
    @GET
    @Path("/process")
    @UnitOfWork
    public ImagesView processImage(@QueryParam("imageUrl") String imageUrl,
                             @QueryParam("params") @DefaultValue("{}") String requestParameters) {
        imageManipulationService.processImage(imageUrl, requestParameters);
        return getAllImages();
    }

    /**
     * Entry point for requesting to process a number of images (set in advance) from the internet as a demo.
     * Better be a POST request, but for the sake of simplicity here I left it as GET.
     *
     * @param requestParameters
     */
    @GET
    @Path("/demo")
    @UnitOfWork
    public ImagesView processDemoImages(@QueryParam("params") @DefaultValue("{}") String requestParameters) {
        imageManipulationService.processDemoImages(requestParameters);
        return getAllImages();
    }

    /**
     * Entry point for requesting DB state.
     */
    @GET
    @Path("/findAll")
    @UnitOfWork
    @Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
    public ImagesView getAllImages() {
        return new ImagesView(imageDao.findAll());
    }

    /**
     * Entry point for requesting DB state.
     */
    @GET
    @Path("/{imageName}")
    @UnitOfWork
    @Produces("image/jpg")
    public Response getImage(@PathParam("imageName") String imageName) {
        return Response.ok(imageManipulationService.getImage(imageName)).build();
    }
}
