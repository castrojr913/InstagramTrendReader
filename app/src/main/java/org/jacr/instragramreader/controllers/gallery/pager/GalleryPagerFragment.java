package org.jacr.instragramreader.controllers.gallery.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.jacr.instragramreader.R;
import org.jacr.instragramreader.controllers.gallery.detail.GalleryDetailActivity_;
import org.jacr.instragramreader.utilities.helpers.ImageHelper;

/**
 * GalleryPagerFragment
 * Created by Jesus on 3/16/2016.
 */
@EFragment(R.layout.fragment_gallery_pager)
public class GalleryPagerFragment extends Fragment {

    //<editor-fold desc="Bundle Data">

    @FragmentArg("bundle_photo_uri")
    protected String photoUri;

    @FragmentArg("bundle_photo_page")
    protected int photoPage;

    //</editor-fold>

    //<editor-fold desc="View Instances">

    @ViewById(R.id.fragment_gallery_pager_imageview)
    protected ImageView photoImageView;

    //</editor-fold>

    @AfterViews
    protected void init() {
        ImageHelper.loadPicture(getActivity(), photoImageView, photoUri);
    }

    //<editor-fold desc="GUI Events">

    @Click(R.id.fragment_gallery_pager_imageview)
    protected void showGalleryDetails() {
        GalleryDetailActivity_.intent(getActivity()).extra("pageToShow", photoPage).start();
    }

    //</editor-fold>

    public static Fragment newInstance(String photoUri, int photoPage) {
        GalleryPagerFragment fragment = new GalleryPagerFragment_();
        Bundle args = new Bundle();
        args.putString("bundle_photo_uri", photoUri);
        args.putInt("bundle_photo_page", photoPage);
        fragment.setArguments(args);
        return fragment;
    }

}