package org.jacr.instragramreader.controllers.gallery.grid;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.EViewGroup;
import org.jacr.instragramreader.R;
import org.jacr.instragramreader.utilities.DataBus;
import org.jacr.instragramreader.utilities.helpers.ImageHelper;

/**
 * GalleryGridImage
 * Created by Jesus on 3/17/2016.
 */
@EViewGroup
public class GalleryGridImage extends FrameLayout {

    //<editor-fold desc="Constants & Variables">

    private final int BORDER_COLOR = R.color.app_gallery_grid_photo_border;

    private static int selectedGridPosition = 0;
    private int gridPosition = 0;
    private boolean isBusRegistered = false;

    //</editor-fold>

    //<editor-fold desc="View Instances"

    private ImageView image;
    private FrameLayout imageLayout;

    //</editor-fold>

    //<editor-fold desc="Constructors">

    public GalleryGridImage(Context context) {
        super(context);
        initializeViews(context);
    }

    public GalleryGridImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    //</editor-fold>

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_gallery_grid_image, this);
    }

    //<editor-fold desc="View Overrides">

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageLayout = (FrameLayout) findViewById(R.id.view_gallery_grid_layout_image);
        image = (ImageView) findViewById(R.id.view_gallery_grid_image);
        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DataBus.getInstance().post(new GalleryGridImageEvent(gridPosition));
            }

        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isBusRegistered) {
            isBusRegistered = true;
            applyImageBorder(true);
            DataBus.getInstance().register(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isBusRegistered) {
            isBusRegistered = false;
            applyImageBorder(false);
            DataBus.getInstance().unregister(this);
        }
    }

    //</editor-fold>

    //<editor-fold desc="GUI Event Handling">

    @Subscribe
    public void onGridSelectedImage(GalleryGridImageEvent event) {
        selectedGridPosition = event.gridPosition;
        imageLayout.setBackgroundColor(gridPosition == selectedGridPosition ?
                ContextCompat.getColor(getContext(), BORDER_COLOR) : Color.TRANSPARENT);
    }

    //</editor-fold>

    //<editor-fold desc="Grid Position & Image">

    public void setGridPosition(int gridPosition) {
        this.gridPosition = gridPosition;
    }

    public void setImageUri(String uri) {
        ImageHelper.loadPicture(getContext(), image, uri);
    }

    private void applyImageBorder(boolean shouldApply) {
        imageLayout.setBackgroundColor(shouldApply && gridPosition == selectedGridPosition ?
                ContextCompat.getColor(getContext(), BORDER_COLOR) : Color.TRANSPARENT);
    }

    public static int getSelectedImagePosition() {
        return selectedGridPosition;
    }

    //</editor-fold>

}