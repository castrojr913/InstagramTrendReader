package org.jacr.instragramreader.utilities.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jacr.instragramreader.R;

/**
 * ImageHelper
 * Created by Jesus on 3/16/2016.
 */
public class ImageHelper {

    public static void loadPicture(Context context, ImageView imageView, String imageUrl) {
        Picasso.with(context).load(imageUrl)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_not_loaded).fit().into(imageView);
    }

}
