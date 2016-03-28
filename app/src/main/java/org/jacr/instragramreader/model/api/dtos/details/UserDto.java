package org.jacr.instragramreader.model.api.dtos.details;

import com.google.gson.annotations.SerializedName;

import org.jacr.instragramreader.model.api.ApiUrls;

import java.io.Serializable;

/**
 * UserDto
 * Created by Jesus on 3/16/2016.
 */
public class UserDto implements Serializable {

    //<editor-fold desc="Variables">

    @SerializedName("full_name")
    private String name;

    @SerializedName("username")
    private String userName;

    //</editor-fold>

    //<editor-fold desc="Getters">

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileUrl() {
        return String.format("%s/%s/", ApiUrls.WEBSITE, userName);
    }

    //</editor-fold>

}
