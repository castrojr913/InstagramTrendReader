package org.jacr.instragramreader.model.api.dtos.details;

import com.google.gson.annotations.SerializedName;

import org.jacr.instragramreader.model.api.dtos.details.images.ImageDto;

import java.io.Serializable;
import java.util.List;

/**
 * PhotoDetailDto
 * Created by Jesus on 3/16/2016.
 */
public class PhotoDetailDto implements Serializable {

    //<editor-fold desc="Variables">

    @SerializedName("images")
    private ImageDto image;

    private List<String> tags;
    private UserDto user;
    private CaptionDto caption;

    //</editor-fold>

    // <editor-fold desc="Getters">

    public String getUserName() {
        return user != null ? user.getName() : "";
    }

    public String getUserProfileUrl(){
        return user != null ? user.getProfileUrl() : "";
    }

    public String getTags() {
        String string = "";
        if (tags != null) {
            for (int i = 0; i < tags.size(); i++) {
                string += String.format("#%s ", tags.get(i) + (i == tags.size() - 1 ? "" : ","));
            }
        }
        return string;
    }

    public String getImageTitle() {
        return caption != null ? caption.getPhotoTitle() : "";
    }

    public String getImagePublishedDate() {
        return caption != null ? caption.getFormattedTime() : "";
    }

    public String getImageThumbnailUrl() {
        return image != null ? image.getThumbnailUrl() : "";
    }

    public String getImageStandardUrl() {
        return image != null ? image.getStandardUrl() : "";
    }

    // </editor-fold>

}
