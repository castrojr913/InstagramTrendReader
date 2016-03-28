package org.jacr.instragramreader.model.api.managers;

import com.google.gson.reflect.TypeToken;

import org.jacr.instragramreader.model.api.ApiUrls;
import org.jacr.instragramreader.model.api.WSError;
import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;
import org.jacr.instragramreader.model.api.dtos.PhotoDto;
import org.jacr.instragramreader.model.api.managers.listeners.ApiListener;
import org.jacr.instragramreader.model.api.managers.listeners.PopularMediaListener;
import org.jacr.instragramreader.utilities.helpers.LogHelper;
import org.jacr.instragramreader.utilities.helpers.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * PopularMediaManager
 * Created by Jesus on 3/16/2016.
 */
public class PopularMediaManager extends ApiManager {

    // <editor-fold desc="Constants">

    private static final Class<?> LOG_TAG = PopularMediaManager.class;
    private static final String PREFERENCE_PHOTO_DETAILS = LOG_TAG.getSimpleName() + ".photoDetails";
    private static final String PREFERENCE_PHOTO_DETAILS_KEY = PREFERENCE_PHOTO_DETAILS + ".key";

    //</editor-fold>

    //<editor-fold desc="Manager Overrides">

    @Override
    protected Class<?> getLogTag() {
        return LOG_TAG;
    }

    @Override
    protected void manageResponse(String url, byte[] response, ApiListener listener) {
        try {
            if (url.equals(ApiUrls.POPULAR_PHOTOS) && listener instanceof PopularMediaListener) {
                PhotoDto dto = (PhotoDto) parseJSONObject(response, PhotoDto.class);
                serializeJSON(PREFERENCE_PHOTO_DETAILS, PREFERENCE_PHOTO_DETAILS_KEY, dto.getPhotoDetails());
                ((PopularMediaListener) listener).onLoadPhotos(dto.getPhotoDetails());
            }
        } catch (Exception e) {
            LogHelper.getInstance().exception(getLogTag(), e);
            listener.onError(WSError.BAD_FORMED_RESPONSE);
        }
    }

    //</editor-fold>

    public void getPhotos(boolean forceUpdate, PopularMediaListener listener) {
        List<PhotoDetailDto> photos = parseSerializedPhotos(PREFERENCE_PHOTO_DETAILS, PREFERENCE_PHOTO_DETAILS_KEY);
        if (forceUpdate || photos.isEmpty()) {
            HashMap<String, String> parameters = new HashMap<String, String>() {{
                put("client_id", "05132c49e9f148ec9b8282af33f88ac7");
            }};
            sendGetRequest(ApiUrls.POPULAR_PHOTOS, parameters, null, listener);
        } else {
            listener.onLoadPhotos(photos);
        }
    }

    protected List<PhotoDetailDto> parseSerializedPhotos(String preference, String preferenceKey) {
        String serializedModel = getPreferences(preference).getString(preferenceKey, null);
        List<PhotoDetailDto> list = new ArrayList<>();
        if (!StringHelper.isEmpty(serializedModel)) {
            list.addAll((List<PhotoDetailDto>) gson.fromJson(serializedModel, new TypeToken<List<PhotoDetailDto>>() {
            }.getType()));
        }
        return list;
    }

}