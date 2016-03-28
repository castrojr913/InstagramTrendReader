package org.jacr.instragramreader.controllers.gallery.detail;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.viewpagerindicator.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.jacr.instragramreader.R;
import org.jacr.instragramreader.controllers.gallery.detail.pager.GalleryDetailPagerAdapter;
import org.jacr.instragramreader.model.api.WSError;
import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;
import org.jacr.instragramreader.model.api.managers.PopularMediaManager;
import org.jacr.instragramreader.model.api.managers.listeners.PopularMediaListener;
import org.jacr.instragramreader.utilities.helpers.MessageHelper;

import java.util.List;

/**
 * GalleryDetailActivity
 * Created by Jesus on 3/16/2016.
 */
@EActivity(R.layout.activity_gallery_detail)
public class GalleryDetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private GalleryDetailPagerAdapter adapter;

    @Extra("pageToShow")
    protected int currentPage;

    //<editor-fold desc="View Instances">

    @ViewById(R.id.activity_gallery_detail_pager_indicator)
    protected CirclePageIndicator pageIndicator;

    @ViewById(R.id.activity_gallery_detail_toolbar)
    protected Toolbar toolbar;

    @ViewById(R.id.activity_gallery_detail_pager)
    protected ViewPager pager;

    @ViewById(R.id.activity_gallery_detail_browser)
    protected WebView webView;

    //</editor-fold>

    //<editor-fold desc="Text Resources">

    @StringRes(R.string.error_connectivity)
    protected String networkError;

    @StringRes(R.string.error_timeout)
    protected String timeoutError;

    @StringRes(R.string.error_webservice)
    protected String webserviceError;

    @StringRes(R.string.gallery_detail_toolbar_share_subject)
    protected String shareContentSubject;

    @StringRes(R.string.gallery_detail_toolbar_share_description)
    protected String shareContentDescription;

    //</editor-fold>

    //<editor-fold desc="Color Resources">

    @ColorRes(R.color.app_toolbar_title_color)
    protected int toolbarTitleColor;

    //</editor-fold>

    //<editor-fold desc="Drawable Resources">

    @DrawableRes(R.drawable.ic_toolbar_image)
    protected Drawable toolbarLogo;

    //</editor-fold>

    @AfterViews
    protected void init() {
        // Toolbar
        toolbar.setLogo(toolbarLogo);
        toolbar.setTitleTextColor(toolbarTitleColor);
        setSupportActionBar(toolbar);
        // Pager
        pager.addOnPageChangeListener(this);
        // Upload Photos from Cache
        PopularMediaManager mediaManager = new PopularMediaManager();
        mediaManager.getPhotos(false, new PopularMediaListener() {

            @Override
            public void onLoadPhotos(List<PhotoDetailDto> photos) {
                displayInformation(photos);
            }

            @Override
            public void onError(WSError errorCode) {
                displayError(errorCode);
            }

        });
    }

    //<editor-fold desc="About Toolbar">

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // It's required to show button on Toolbar
        getMenuInflater().inflate(R.menu.menu_gallery_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        /* When user clicks on some Toolbar button  */
        switch (item.getItemId()) {
            case R.id.menu_gallery_detail_share:
                MessageHelper.shareMessage(this, shareContentSubject,
                        String.format("%s \n%s", shareContentDescription,
                                adapter.getPhotoDetail(currentPage).getImageStandardUrl()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //</editor-fold>

    //<editor-fold desc="GUI Event Handling">

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Blank
    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        updateToolbarTitle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Blank
    }

    //</editor-fold>

    //<editor-fold desc="Display Error & Data">

    private void updateToolbarTitle() {
        toolbar.setTitle(adapter.getPhotoDetail(currentPage).getImageTitle());
    }

    @UiThread
    protected void displayInformation(List<PhotoDetailDto> photos) {
        adapter = new GalleryDetailPagerAdapter(getSupportFragmentManager(), photos);
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentPage);
        pageIndicator.setViewPager(pager);
        updateToolbarTitle();
    }

    protected void showProfile() {
        webView.loadUrl(adapter.getPhotoDetail(currentPage).getUserProfileUrl());
        webView.setVisibility(View.GONE);
    }

    @UiThread
    protected void displayError(WSError errorCode) {
        MessageHelper.showMessage(this, errorCode == WSError.CONNECTIVITY_FAILURE ? networkError :
                errorCode == WSError.TIMEOUT_FAILURE ? timeoutError : webserviceError);
    }

    //</editor-fold>

}