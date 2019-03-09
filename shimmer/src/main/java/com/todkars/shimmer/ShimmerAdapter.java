package com.todkars.shimmer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

final class ShimmerAdapter extends RecyclerView.Adapter<ShimmerViewHolder> {

    private Shimmer shimmer;

    @LayoutRes
    private int layout;

    private int itemCount = 0;

    ShimmerAdapter(@LayoutRes int layout, int itemCount, Shimmer shimmer) {
        this.layout = layout;
        if (itemCount > 0) this.itemCount = itemCount;
        this.shimmer = shimmer;
    }

    @NonNull
    @Override
    public ShimmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shimmer_viewholder_layout, parent, false);
        return new ShimmerViewHolder((ShimmerFrameLayout) inflater
                .inflate(layout, (ShimmerFrameLayout) view, true));
    }

    @Override
    public void onBindViewHolder(@NonNull ShimmerViewHolder holder, int position) {
        holder.updateShimmer(shimmer)
                .bindView();
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
        this.itemCount = count;
    }
}
