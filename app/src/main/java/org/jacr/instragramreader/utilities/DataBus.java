package org.jacr.instragramreader.utilities;

import com.squareup.otto.Bus;

/**
 * DataBus
 * Created by Jesus on 3/17/2016.
 */
public class DataBus {

    /*
     * This class will set the communication channel up among different views so that they
     * can share date themselves
     */

    private static DataBus singleton;
    private final Bus bus;

    //<editor-fold desc="Singleton">

    private DataBus() {
        bus = new Bus();
    }

    public static Bus getInstance() {
        if (singleton == null) {
            singleton = new DataBus();
        }
        return singleton.getBus();
    }

    //</editor-fold>

    private Bus getBus() {
        return bus;
    }

}
