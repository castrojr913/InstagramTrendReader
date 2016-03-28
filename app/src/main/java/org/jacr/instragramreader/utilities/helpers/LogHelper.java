package org.jacr.instragramreader.utilities.helpers;

import java.util.Map;

import timber.log.Timber;

/**
 * LogHelper
 * Created by Jesus on 3/16/2016.
 */
public class LogHelper {

    private static LogHelper singleton;

    //<editor-fold desc="Singleton">

    private LogHelper() {
        // blank
    }

    public static LogHelper getInstance() {
        if (singleton == null) {
            singleton = new LogHelper();
            Timber.plant(new Timber.DebugTree());
        }
        return singleton;
    }

    //</editor-fold">

    //<editor-fold desc="General Purpose Timbers">

    public void debug(Class<?> sourceClass, String message) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).d(message);
    }

    public void exception(Class<?> sourceClass, Throwable exception) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).e(exception, exception.getMessage());
    }

    //</editor-fold>

    //<editor-fold desc="Http Requests Timbers">

    private String buildResponseMessage(String url, String requestType, int statusCode, byte[] responseBody) {
        final String separator = "_____________________";
        return String.format("\n%s " +
                "\n- URL:%s " +
                "\n- STATUS_CODE:%d " +
                "\n- BODY:'%s' \n"
                + separator, requestType, url, statusCode, responseBody != null ? new String(responseBody) : "");
    }

    private String buildRequestMessage(String url, String requestType, String parameters, String headers) {
        final String separator = "_____________________";
        return String.format("\n%s " +
                "\n- URL:%s \n" +
                "\n- PARAMETERS:%s \n" +
                "\n- HEADERS:%s \n"
                + separator, requestType, url, parameters, headers);
    }

    public void debugRequest(Class<?> sourceClass, String url, String requestType, Map paramaters, Map headers) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).d(
                buildRequestMessage(url, requestType, paramaters != null ? paramaters.toString() : null,
                        headers != null ? headers.toString() : null));
    }

    public void debugResponse(Class<?> sourceClass, String url, String requestType, int statusCode, byte[] responseBody) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).d(buildResponseMessage(url, requestType, statusCode, responseBody));
    }

    public void errorResponse(Class<?> sourceClass, String url, String requestType, int statusCode, byte[] responseBody) {
        Timber.tag(sourceClass.getSimpleName().toUpperCase()).e(buildResponseMessage(url, requestType, statusCode, responseBody));
    }

    //</editor-fold>

}