package org.jacr.instragramreader.controllers.gallery.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;

import java.util.List;

/**
 * GalleryPagerAdapter
 * Created by Jesus on 3/16/2016.
 */
public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private final List<PhotoDetailDto> photos;

    public GalleryPagerAdapter(FragmentManager fragmentManager, List<PhotoDetailDto> photos) {
        super(fragmentManager);
        this.photos = photos;
    }

    //<editor-fold desc="Adapter Overrides">

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Fragment getItem(int pageIndex) {
        return GalleryPagerFragment.newInstance(photos.get(pageIndex).getImageThumbnailUrl(), pageIndex);
    }

    //</editor-fold>

}