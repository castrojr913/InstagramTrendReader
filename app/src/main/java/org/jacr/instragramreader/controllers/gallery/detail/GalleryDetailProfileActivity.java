package org.jacr.instragramreader.controllers.gallery.detail;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.jacr.instragramreader.R;
import org.jacr.instragramreader.utilities.helpers.MessageHelper;

/**
 * GalleryDetailProfileActivity
 * Created by Jesus on 3/16/2016.
 */
@EActivity(R.layout.activity_gallery_detail_profile)
public class GalleryDetailProfileActivity extends AppCompatActivity {

    //<editor-fold desc="Bundle / Extras">

    @Extra("profileUrl")
    protected String profileUrl;

    @Extra("username")
    protected String username;

    //</editor-fold>

    //<editor-fold desc="View Instances">

    @ViewById(R.id.activity_gallery_detail_toolbar)
    protected Toolbar toolbar;

    @ViewById(R.id.activity_gallery_detail_browser)
    protected WebView webView;

    //</editor-fold>

    //<editor-fold desc="Text Resources">

    @StringRes(R.string.gallery_detail_profile_toolbar_share_subject)
    protected String shareContentSubject;

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
        toolbar.setTitle(username);
        setSupportActionBar(toolbar);
        // Webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false; // False -> Webview handles redirection of url
            }

        });
        // Load url
        webView.loadUrl(profileUrl);
    }

    //<editor-fold desc="About Toolbar">

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // It's required to show button on Toolbar
        getMenuInflater().inflate(R.menu.menu_gallery_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* When user clicks on some Toolbar button  */
        switch (item.getItemId()) {
            case R.id.menu_gallery_detail_share:
                MessageHelper.shareMessage(this, shareContentSubject, profileUrl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //</editor-fold>

}