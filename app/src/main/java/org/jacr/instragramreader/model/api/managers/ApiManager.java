package org.jacr.instragramreader.model.api.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jacr.instragramreader.RootApplication;
import org.jacr.instragramreader.model.api.WSError;
import org.jacr.instragramreader.model.api.managers.listeners.ApiListener;
import org.jacr.instragramreader.utilities.helpers.LogHelper;
import org.jacr.instragramreader.utilities.helpers.NetworkHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiManager
 * Created by Jesus on 3/15/2016.
 */
public abstract class ApiManager {

    /*
     This template class handles the error outcomes in regard to webservice answers,
     delegates the concrete managers / daos the answer processing and it offers for their subclasses
     useful methods to parse and serialize JSON responses.
     */

    protected final Gson gson = new Gson();

    //<editor-fold desc="Abstract Methods">

    protected abstract Class<?> getLogTag();

    protected abstract void manageResponse(String url, byte[] response, ApiListener listener);

    //</editor-fold>

    //<editor-fold desc="Response & Request Management">

    protected <L extends ApiListener> void sendGetRequest(String url, HashMap parameters, HashMap headers, L listener) {
        if (NetworkHelper.getInstance().isConnected()) {
            LogHelper.getInstance().debugRequest(getLogTag(), "GET", url, parameters, headers);
            NetworkHelper.getInstance().get(url, parameters,
                    headers == null || headers.isEmpty() ? buildDefaultHeaders() : headers,
                    buildApiResponse(url, listener));
        } else {
            listener.onError(WSError.CONNECTIVITY_FAILURE);
        }
    }

    private Callback buildApiResponse(final String url, final ApiListener listener) {
        final Class<?> logTag = getLogTag();
        return new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                WSError error;
                String exception = e.toString();
                if (exception.contains("ConnectException") || exception.contains("UnknownHostException")) {
                    error = WSError.CONNECTIVITY_FAILURE;
                } else if (exception.contains("SocketTimeoutException")) {
                    error = WSError.TIMEOUT_FAILURE;
                } else {
                    error = WSError.WEBSERVICE_FAILURE;
                }
                LogHelper.getInstance().exception(logTag, e);
                listener.onError(error); // Notify error
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String requestType = response.request().method();
                byte[] responseStream = response.body().bytes();
                if (response.isSuccessful()) { // status code: [200, 300)
                    LogHelper.getInstance().debugResponse(logTag, url, requestType, response.code(), responseStream);
                    manageResponse(url, responseStream, listener); // Delegate the concrete manager / dao the rest of the process
                } else {
                    LogHelper.getInstance().errorResponse(logTag, url, requestType, response.code(), responseStream);
                    listener.onError(WSError.WEBSERVICE_FAILURE); // Notify error
                }
            }

        };
    }

    private Map<String, String> buildDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    //</editor-fold>

    //<editor-fold desc="Cache & Parsing Management">

    protected Object parseJSONObject(byte[] jsonResponse, Class<?> dtoClass) {
        return gson.fromJson(new String(jsonResponse), dtoClass);
    }

    protected void serializeJSON(String preference, String preferenceKey, Object dto) {
        SharedPreferences.Editor preferencesEditor = getPreferences(preference).edit();
        preferencesEditor.putString(preferenceKey, gson.toJson(dto));
        preferencesEditor.apply();
    }

    protected SharedPreferences getPreferences(String preference) {
        return RootApplication.getInstance().getSharedPreferences(preference, Context.MODE_PRIVATE);
    }

    //</editor-fold>s

}