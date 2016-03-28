package org.jacr.instragramreader.model.api.dtos.details;

import com.google.gson.annotations.SerializedName;

import org.jacr.instragramreader.utilities.enums.DateFormat;
import org.jacr.instragramreader.utilities.helpers.DateHelper;

import java.io.Serializable;

/**
 * CaptionDto
 * Created by Jesus on 3/16/2016.
 */
public class CaptionDto implements Serializable {

    //<editor-fold desc="Variables">

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("text")
    private String photoTitle;

    //</editor-fold>

    //<editor-fold desc="Getters">

    public String getFormattedTime() {
        return DateHelper.formatUnixTime(Long.parseLong(createdTime), DateFormat.TYPE_I.getFormat());
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    //</editor-fold>

}
