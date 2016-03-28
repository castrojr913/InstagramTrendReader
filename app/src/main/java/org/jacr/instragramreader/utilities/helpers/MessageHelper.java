package org.jacr.instragramreader.utilities.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.gc.materialdesign.widgets.SnackBar;

import org.jacr.instragramreader.R;

/**
 * MessageHelper
 * Created by Jesus on 3/20/2016.
 */
public class MessageHelper {

    public static void showMessage(Activity activity, String message) {
        // Workaround: It's required to set an instance anonymous from View.OnClickListener in order to show
        // the button so that user can close the message. However, we don't need to process anything
        // inside onClick method
        SnackBar snackbar = new SnackBar(activity, message, activity.getString(R.string.button_ok),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Blank
                    }
                });
        snackbar.setIndeterminate(true);
        snackbar.show();
    }

    public static void shareMessage(Activity activity, String subject, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        activity.startActivity(Intent.createChooser(sendIntent,
                activity.getString(R.string.toolbar_share_title)));
    }

}
