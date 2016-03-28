package org.jacr.instragramreader.utilities.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * DateHelper
 * Created by Jesus on 3/16/2016.
 */
public class DateHelper {

    public static String formatUnixTime(long unixTime, String patternToConvert) {
        Date date = new Date(unixTime*1000L); // seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat(patternToConvert, Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

}
