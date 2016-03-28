package org.jacr.instragramreader.model.api;

/**
 * WSError
 * Created by Jesus on 3/16/2016.
 */
public enum WSError {

    /*
       These values are used to communicate errors from Model layer to Controller
     */

    CONNECTIVITY_FAILURE,
    TIMEOUT_FAILURE,
    WEBSERVICE_FAILURE,
    BAD_FORMED_RESPONSE,

}
