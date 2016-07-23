package com.cortica.interview.views;

import com.cortica.interview.api.Constants;
import com.cortica.interview.db.ImageEntity;
import io.dropwizard.views.View;

import java.util.List;

/**
 * Created by avgdorom on 7/23/2016.
 */
public class ImagesView extends View {

    private List<ImageEntity> images;

    public ImagesView(List<ImageEntity> images) {
        super(Constants.IMAGES_VIEW_TEMPLATE);
        this.images = images;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public int getNumberOfImagesProcessed() {
        return images.size();
    }
}
