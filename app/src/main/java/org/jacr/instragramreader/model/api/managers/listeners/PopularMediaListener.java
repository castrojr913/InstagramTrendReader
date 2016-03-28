package org.jacr.instragramreader.model.api.managers.listeners;

import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;

import java.util.List;

/**
 * PopularMediaListener
 * Created by Jesus on 3/16/2016.
 */
public interface PopularMediaListener extends ApiListener {

    void onLoadPhotos(List<PhotoDetailDto> photos);

}
