package com.todkars.shimmer;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

final class ShimmerViewHolder extends RecyclerView.ViewHolder {

    private final ShimmerFrameLayout mShimmer;

    private Shimmer settings;

    ShimmerViewHolder(@NonNull ShimmerFrameLayout itemView) {
        super(itemView);
        this.mShimmer = itemView;
    }

    final ShimmerViewHolder updateSettings(Shimmer settings) {
        this.settings = settings;
        return this;
    }

    /**
     * Updates shimmer settings and then starts shimmer,
     * if not shimmering already.
     */
    final void bindView() {
        if (mShimmer.isShimmerStarted())
            mShimmer.stopShimmer();

        mShimmer.setShimmer(settings);
        mShimmer.startShimmer();
    }
}
