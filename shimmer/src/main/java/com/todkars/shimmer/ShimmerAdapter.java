/*
ShimmerRecyclerView a custom RecyclerView library
Copyright (C) 2019  Omkar Todkar

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see below link
https://github.com/omtodkar/ShimmerRecyclerView/blob/master/LICENSE.md
*/
package com.todkars.shimmer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.todkars.shimmer.ShimmerRecyclerView.LayoutType;

public final class ShimmerAdapter extends RecyclerView.Adapter<ShimmerViewHolder> {

    /**
     * A contract to change shimmer view type.
     */
    public interface ItemViewType {

        @LayoutRes
        int getItemViewType(@LayoutType int layoutManagerType, int position);
    }

    private Shimmer shimmer;

    @LayoutRes
    private int layout;

    private int itemCount;

    private int layoutManagerType;

    private ItemViewType itemViewType;


    @RecyclerView.Orientation
    private int mLayoutOrientation;


    ShimmerAdapter(@LayoutRes int layout, int itemCount, int layoutManagerType,
                   ItemViewType itemViewType, Shimmer shimmer, int layoutOrientation) {
        this.layout = layout;
        this.itemCount = validateCount(itemCount);
        this.layoutManagerType = layoutManagerType;
        this.itemViewType = itemViewType;
        this.shimmer = shimmer;
        this.mLayoutOrientation = layoutOrientation;
    }

    @Override
    public int getItemViewType(int position) {
        return (itemViewType != null)
                ? itemViewType.getItemViewType(layoutManagerType, position)
                : layout; /* default */
    }

    @NonNull
    @Override
    public ShimmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        /* inflate view holder layout and then attach provided view in it. */
        View view = inflater.inflate(R.layout.recyclerview_shimmer_viewholder_layout,
                parent, false);

        if (mLayoutOrientation == RecyclerView.HORIZONTAL) {
            view.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        return new ShimmerViewHolder((ShimmerFrameLayout) inflater
                .inflate(viewType, (ShimmerFrameLayout) view,
                        true /* attach to view holder layout */));
    }

    @Override
    public void onBindViewHolder(@NonNull ShimmerViewHolder holder, int position) {
        holder.bindView(shimmer);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal APIs
    ///////////////////////////////////////////////////////////////////////////

    void setShimmer(Shimmer shimmer) {
        this.shimmer = shimmer;
    }

    void setLayout(@LayoutRes int layout) {
        this.layout = layout;
    }

    void setCount(int count) {
        this.itemCount = validateCount(count);
    }

    void setShimmerItemViewType(@LayoutType int layoutManagerType, ItemViewType itemViewType) {
        this.layoutManagerType = layoutManagerType;
        this.itemViewType = itemViewType;
    }

    /**
     * Validates if provided item count is greater than reasonable number
     * of items and returns max number of items allowed.
     * <p>
     * Try to save memory produced by shimmer layouts.
     *
     * @param count input number.
     * @return valid count number.
     */
    private int validateCount(int count) {
        return count < 20 ? count : 20;
    }
}
