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

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

final class ShimmerViewHolder extends RecyclerView.ViewHolder {

    private final ShimmerFrameLayout mShimmer;

    private Shimmer shimmer;

    ShimmerViewHolder(@NonNull ShimmerFrameLayout itemView) {
        super(itemView);
        this.mShimmer = itemView;
    }

    final ShimmerViewHolder updateShimmer(Shimmer settings) {
        this.shimmer = settings;
        return this;
    }

    /**
     * Updates shimmer properties and then starts shimmer,
     * if not shimmering already.
     */
    final void bindView() {
        if (mShimmer.isShimmerStarted())
            mShimmer.stopShimmer();

        mShimmer.setShimmer(shimmer);
        mShimmer.startShimmer();
    }
}
