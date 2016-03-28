package org.jacr.instragramreader.utilities.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.jacr.instragramreader.RootApplication;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * NetworkHelper
 * Created by Jesus on 3/17/2016.
 */
public class NetworkHelper {

    private static NetworkHelper singleton;
    private final OkHttpClient client;

    // <editor-fold desc="Singleton">

    private NetworkHelper() {
        client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(15, TimeUnit.SECONDS);
        client.setWriteTimeout(15, TimeUnit.SECONDS);
    }

    public static NetworkHelper getInstance() {
        if (singleton == null) {
            singleton = new NetworkHelper();
        }
        return singleton;
    }

    //</editor-fold>

    // <editor-fold desc="Network Status">

    public boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager) RootApplication.getInstance().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    // </editor-fold>

    //<editor-fold desc="Http Headers">

    private Headers buildHeaders(@NonNull Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            builder.add(key, headers.get(key));
        }
        return builder.build();
    }

    //</editor-fold>

    //<editor-fold desc="GET Requests">

    public void get(@NonNull String url, Map<String, String> parameters, @NonNull Map<String, String> headers,
                    @NonNull Callback callback) {
        Request.Builder builder = new Request.Builder();
        builder.url(url + (parameters != null && parameters.size() != 0 ? buildGetParameters(parameters) : ""));
        builder.headers(buildHeaders(headers));
        Request request = builder.build();
        client.newCall(request).enqueue(callback);
    }

    private String buildGetParameters(Map<String, String> params) {
        String string = "?";
        Set<String> keys = params.keySet();
        for (String key : keys) {
            string += String.format("%s=%s&", key, params.get(key));
        }
        return string.substring(0, string.lastIndexOf("&"));
    }

    //</editor-fold>

}
