package org.jacr.instragramreader;

import android.app.Application;

/**
 * RootApplication
 * Created by Jesus on 3/17/2016.
 */
public class RootApplication extends Application {

     /*
     * This class is executed once time. Therefore, we consider its instance as a Singleton.
     */

    private static RootApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static RootApplication getInstance() {
        return application;
    }

}
