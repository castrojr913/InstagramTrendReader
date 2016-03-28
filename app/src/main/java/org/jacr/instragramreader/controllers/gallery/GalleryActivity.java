package org.jacr.instragramreader.controllers.gallery;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.jacr.instragramreader.R;
import org.jacr.instragramreader.controllers.gallery.grid.GalleryGridAdapter;
import org.jacr.instragramreader.controllers.gallery.grid.GalleryGridImage;
import org.jacr.instragramreader.controllers.gallery.grid.GalleryGridImageEvent;
import org.jacr.instragramreader.controllers.gallery.pager.GalleryPagerAdapter;
import org.jacr.instragramreader.model.api.WSError;
import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;
import org.jacr.instragramreader.model.api.managers.PopularMediaManager;
import org.jacr.instragramreader.model.api.managers.listeners.PopularMediaListener;
import org.jacr.instragramreader.utilities.DataBus;
import org.jacr.instragramreader.utilities.helpers.MessageHelper;
import org.jacr.instragramreader.utilities.views.ScrollableSwipeRefreshLayout;

import java.util.List;

/**
 * GalleryActivity
 * Created by Jesus on 3/16/2016.
 */
@EActivity(R.layout.activity_gallery)
public class GalleryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        ViewPager.OnPageChangeListener {

    private static final int GRID_COLUMNS = 3;

    //<editor-fold desc="View Instances"

    @ViewById(R.id.activity_gallery_layout_refresh)
    protected ScrollableSwipeRefreshLayout swipeToRefreshLayout;

    @ViewById(R.id.activity_gallery_toolbar)
    protected Toolbar toolbar;

    @ViewById(R.id.activity_gallery_pager)
    protected ViewPager pager;

    @ViewById(R.id.activity_gallery_grid)
    protected RecyclerView gridView;

    @ViewById(R.id.activity_gallery_progressbar_circular)
    protected ProgressBarCircularIndeterminate progressBar;

    @ViewById(R.id.activity_gallery_textview_no_items)
    protected TextView noItemsTextView;

    //</editor-fold>

    //<editor-fold desc="Text Resources">

    @StringRes(R.string.gallery_title)
    protected String toolbarTitle;

    @StringRes(R.string.error_connectivity)
    protected String networkError;

    @StringRes(R.string.error_timeout)
    protected String timeoutError;

    @StringRes(R.string.error_webservice)
    protected String webserviceError;

    //</editor-fold>

    //<editor-fold desc="Color Resources">

    @ColorRes(R.color.app_refresh_i)
    protected int firstRefreshColor;

    @ColorRes(R.color.app_refresh_ii)
    protected int secondRefreshColor;

    @ColorRes(R.color.app_toolbar_title_color)
    protected int toolbarTitleColor;

    //</editor-fold>

    //<editor-fold desc="Drawable Resources">

    @DrawableRes(R.drawable.ic_toolbar_home)
    protected Drawable toolbarLogo;

    //</editor-fold>

    @AfterViews
    protected void init() {
        // Toolbar
        toolbar.setLogo(toolbarLogo);
        toolbar.setTitle(toolbarTitle);
        toolbar.setTitleTextColor(toolbarTitleColor);
        setSupportActionBar(toolbar);
        // RecyclerView (for thumbnails grid)
        gridView.setLayoutManager(new GridLayoutManager(this, GRID_COLUMNS));
        // Swipe to Refresh
        swipeToRefreshLayout.setColorSchemeColors(firstRefreshColor, secondRefreshColor);
        swipeToRefreshLayout.setRecyclerView(gridView);
        swipeToRefreshLayout.setOnRefreshListener(this);
        // Pager (Preview Image)
        pager.addOnPageChangeListener(this);
        pager.arrowScroll(ViewPager.SCROLL_STATE_DRAGGING);
        // Register to receive events from Otto Bus. This is important to pick out the preview image
        // If user touches a thumbnail
        DataBus.getInstance().register(this);
        // Load cached Photos on grid, otherwise get them from the webservice
        showProgressView();
        loadUpdatedPhotos(false);
    }

    //<editor-fold desc="Activity Overrides">

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // It's required to show button on Toolbar
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        DataBus.getInstance().unregister(this);
        super.onDestroy();
    }

    //</editor-fold>

    //<editor-fold desc="GUI Events Handling">

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_gallery_update:
                showProgressView();
                loadUpdatedPhotos(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadUpdatedPhotos(true);
    }

    @Override
    public void onPageSelected(int pageIndex) {
        // Page index matches with the grid position.
        DataBus.getInstance().post(new GalleryGridImageEvent(pageIndex));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Blank
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // ViewPager has troubles to swipe the next page with the SwipeToRefresh. This code line fixes it.
        swipeToRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
    }

    @Subscribe
    public void onGridSelectedImage(GalleryGridImageEvent event) {
        pager.setCurrentItem(event.gridPosition);
    }

    //</editor-fold>

    private void loadUpdatedPhotos(boolean forceUpdate) {
        PopularMediaManager mediaManager = new PopularMediaManager();
        mediaManager.getPhotos(forceUpdate, new PopularMediaListener() {

            @Override
            public void onLoadPhotos(List<PhotoDetailDto> photos) {
                displayDataForPhotosView(photos);
            }

            @Override
            public void onError(WSError errorCode) {
                displayError(errorCode);
            }

        });
    }

    //<editor-fold desc="Error Messages & Data Display">

    @UiThread
    protected void displayDataForPhotosView(List<PhotoDetailDto> photos) {
        hideLoadingViews();
        showNoItemsView(photos.isEmpty());
        if (!photos.isEmpty()) {
            gridView.setAdapter(new GalleryGridAdapter(photos));
            pager.setAdapter(new GalleryPagerAdapter(getSupportFragmentManager(), photos));
            pager.setCurrentItem(GalleryGridImage.getSelectedImagePosition());
        }
    }

    @UiThread
    protected void displayError(WSError errorCode) {
        hideLoadingViews();
        MessageHelper.showMessage(this, errorCode == WSError.CONNECTIVITY_FAILURE ? networkError :
                errorCode == WSError.TIMEOUT_FAILURE ? timeoutError : webserviceError);
    }

    private void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
        showContentView(false);
    }

    private void hideLoadingViews() {
        progressBar.setVisibility(View.GONE);
        swipeToRefreshLayout.setRefreshing(false);
        showContentView(true);
    }

    private void showNoItemsView(boolean isVisible) {
        noItemsTextView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        showContentView(!isVisible);
    }

    private void showContentView(boolean isVisible) {
        gridView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        pager.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    //</editor-fold>

}