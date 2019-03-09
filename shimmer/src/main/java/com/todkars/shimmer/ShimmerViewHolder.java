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
