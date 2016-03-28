package org.jacr.instragramreader.controllers.gallery.grid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jacr.instragramreader.R;
import org.jacr.instragramreader.model.api.dtos.details.PhotoDetailDto;

import java.util.List;

/**
 * GalleryGridAdapter
 * Created by Jesus on 3/16/2016.
 */
public class GalleryGridAdapter extends RecyclerView.Adapter<GalleryGridAdapter.ViewHolder> {

    private final List<PhotoDetailDto> items;

    public GalleryGridAdapter(List<PhotoDetailDto> items) {
        this.items = items;
    }

    // <editor-fold desc="View Holder">

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView photoTitleTextView;
        final GalleryGridImage photoImageView;

        public ViewHolder(View view) {
            super(view);
            photoTitleTextView = (TextView) view.findViewById(R.id.adapter_gallery_grid_title);
            photoImageView = (GalleryGridImage) view.findViewById(R.id.adapter_gallery_grid_image);
        }

    }

    //</editor-fold>

    //<editor-fold desc="Adapter Overrides">

    // Create new views (invoked by the layout manager)
    @Override
    public GalleryGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery_grid, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoDetailDto dto = items.get(position);
        holder.photoTitleTextView.setText(dto.getImageTitle());
        holder.photoImageView.setGridPosition(position);
        holder.photoImageView.setImageUri(dto.getImageThumbnailUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //</editor-fold>

}