package org.jacr.instragramreader.model.api.dtos;

import com.google.gson.annotations.SerializedName;

import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;

import java.util.Collections;
import java.util.List;

/**
 * PhotoDto
 * Created by Jesus on 3/16/2016.
 */
public class PhotoDto {

    @SerializedName("data")
    private List<PhotoDetailDto> photoDetails;

    public List<PhotoDetailDto> getPhotoDetails() {
        return photoDetails != null ? photoDetails : Collections.EMPTY_LIST;
    }

}
