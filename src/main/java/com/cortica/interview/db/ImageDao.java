package com.cortica.interview.db;

import com.cortica.interview.ImageFactoryApplication;
import io.dropwizard.hibernate.AbstractDAO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by avgdorom on 7/20/2016.
 */
@Service("imageDao")
public class ImageDao extends AbstractDAO<ImageEntity> {

    public ImageDao() {
        super(ImageFactoryApplication.HIBERNATE_BUNDLE.getSessionFactory());
    }

    public List<ImageEntity> findAll() {
        return list(namedQuery(ImageEntity.QUERY_FIND_ALL));
    }

    public ImageEntity findById(Long id) {
        return get(id);
    }

    @Override
    public ImageEntity persist(ImageEntity imageEntity) {
        return super.persist(imageEntity);
    }
}
