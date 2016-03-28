package org.jacr.instragramreader.model.api.dtos.details.images;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * ImageDto
 * Created by Jesus on 3/16/2016.
 */
public class ImageDto implements Serializable {

    //<editor-fold desc="Variables">

    @SerializedName("thumbnail")
    private ThumbImageDto thumbnail;

    @SerializedName("standard_resolution")
    private StandardImageDto standard;

    //</editor-fold>

    //<editor-fold desc="Getters">

    public String getThumbnailUrl() {
        return thumbnail != null ? thumbnail.getUrl() : "";
    }

    public String getStandardUrl() {
        return standard != null ? standard.getUrl() : "";
    }

    //</editor-fold>

}
