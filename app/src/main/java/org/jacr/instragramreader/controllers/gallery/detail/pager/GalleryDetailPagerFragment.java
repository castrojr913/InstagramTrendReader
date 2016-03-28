package org.jacr.instragramreader.controllers.gallery.detail.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.jacr.instragramreader.R;
import org.jacr.instragramreader.controllers.gallery.detail.GalleryDetailProfileActivity_;
import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;
import org.jacr.instragramreader.utilities.helpers.ImageHelper;

/**
 * GalleryDetailPagerFragment
 * Created by Jesus on 3/20/2016.
 */
@EFragment(R.layout.fragment_gallery_detail_pager)
public class GalleryDetailPagerFragment extends Fragment {

    //<editor-fold desc="Bundle Data">

    @FragmentArg("bundle_photo_dto")
    protected PhotoDetailDto photoDetail;

    //</editor-fold>

    //<editor-fold desc="View Instances">

    @ViewById(R.id.fragment_gallery_detail_pager_imageview)
    protected ImageView photoImageView;

    @ViewById(R.id.fragment_gallery_detail_pager_textview_published_date_value)
    protected TextView publishedDateTextView;

    @ViewById(R.id.fragment_gallery_detail_pager_textview_author_value)
    protected TextView authorTextView;

    @ViewById(R.id.fragment_gallery_detail_pager_textview_tag_value)
    protected TextView tagsTextView;

    //</editor-fold>

    @AfterViews
    protected void init() {
        ImageHelper.loadPicture(getActivity(), photoImageView, photoDetail.getImageStandardUrl());
        authorTextView.setText(photoDetail.getUserName());
        publishedDateTextView.setText(photoDetail.getImagePublishedDate());
        tagsTextView.setText(photoDetail.getTags());
    }

    //<editor-fold desc="GUI Events">

    @Click(R.id.fragment_gallery_detail_pager_imageview)
    protected void onImageClicked() {
        GalleryDetailProfileActivity_.intent(getActivity())
                .extra("profileUrl", photoDetail.getUserProfileUrl())
                .extra("username", photoDetail.getUserName())
                .start();
    }

    //</editor-fold>

    public static Fragment newInstance(PhotoDetailDto dto) {
        GalleryDetailPagerFragment fragment = new GalleryDetailPagerFragment_();
        Bundle args = new Bundle();
        args.putSerializable("bundle_photo_dto", dto);
        fragment.setArguments(args);
        return fragment;
    }

}