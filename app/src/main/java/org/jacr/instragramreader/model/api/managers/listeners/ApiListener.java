package org.jacr.instragramreader.model.api.managers.listeners;

import org.jacr.instragramreader.model.api.WSError;

/**
 * ApiListener
 * Created by Jesus on 3/16/2016.
 */
public interface ApiListener {

    /*
     * All basic methods in order to handle Webservice answers will be declared here
     * so that every listener extends them
     */

    void onError(WSError errorCode);

}
