package org.jacr.instragramreader.controllers.gallery.detail.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;

import java.util.List;

/**
 * GalleryDetailPagerAdapter
 * Created by Jesus on 3/20/2016.
 */
public class GalleryDetailPagerAdapter extends FragmentStatePagerAdapter {

    private final List<PhotoDetailDto> photos;

    public GalleryDetailPagerAdapter(FragmentManager fragmentManager, List<PhotoDetailDto> photos) {
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
        return GalleryDetailPagerFragment.newInstance(getPhotoDetail(pageIndex));
    }

    public PhotoDetailDto getPhotoDetail(int pageIndex) {
        return photos.get(pageIndex);
    }

    //</editor-fold>

}
