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

public final class ShimmerAdapter extends RecyclerView.Adapter<ShimmerViewHolder> {

    private Shimmer shimmer;

    @LayoutRes
    private int layout;

    private int itemCount;

    ShimmerAdapter(@LayoutRes int layout, int itemCount, Shimmer shimmer) {
        this.layout = layout;
        this.itemCount = validateCount(itemCount);
        this.shimmer = shimmer;
    }

    @NonNull
    @Override
    public ShimmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_shimmer_viewholder_layout, parent, false);
        return new ShimmerViewHolder((ShimmerFrameLayout) inflater
                .inflate(layout, (ShimmerFrameLayout) view, true));
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
