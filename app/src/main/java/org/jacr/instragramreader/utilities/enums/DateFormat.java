package org.jacr.instragramreader.utilities.enums;

/**
 * DateFormat
 * Created by Jesus on 3/16/2016.
 */
public enum DateFormat {

    /* Declare all sort of Date formats/patterns here which the application needs itself */

    TYPE_I {
        @Override
        public String getFormat() {
            return "EEE, dd MMM yyyy 'at' HH:mm";
        }
    };

    public abstract String getFormat();

}
