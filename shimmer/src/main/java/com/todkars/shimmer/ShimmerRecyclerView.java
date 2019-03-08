package com.todkars.shimmer;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.shimmer.Shimmer;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public final class ShimmerRecyclerView extends RecyclerView {

    static final int LINEAR_HORIZONTAL = RecyclerView.HORIZONTAL;

    static final int LINEAR_VERTICAL = RecyclerView.VERTICAL;

    static int GRID = 2;

    private ShimmerAdapter mShimmerAdapter;

    private Adapter mActualAdapter;

    private LayoutManager mLayoutManager;

    private boolean mScrollEnabled;

    @RecyclerView.Orientation
    private int mLayoutOrientation = LINEAR_VERTICAL;

    private int mGridSpanCount = -1;

    @LayoutRes
    private int shimmerLayout;

    private int shimmerItemCount;

    private Shimmer settings;

    public ShimmerRecyclerView(@NonNull Context context) {
        super(context);
        initialize(context, null);
    }

    public ShimmerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public ShimmerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context, attrs);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overridden methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {
            mGridSpanCount = ((GridLayoutManager) layout).getSpanCount();
        } else if (layout instanceof LinearLayoutManager) {
            mLayoutOrientation = ((LinearLayoutManager) layout).getOrientation();
        }

        if (mShimmerAdapter != null) {
            mShimmerAdapter.setLayout(shimmerLayout);
            mShimmerAdapter.setCount(shimmerItemCount);
            mShimmerAdapter.setSettings(settings);

            mShimmerAdapter.notifyDataSetChanged();
        }
        super.setLayoutManager(layout);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public APIs
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param layout layout reference for shimmer adapter.
     */
    public void setShimmerLayout(@LayoutRes int layout) {
        this.shimmerLayout = layout;
    }

    /**
     * @param count Number of items to be shown in shimmer adapter.
     */
    public void setShimmerItemCount(int count) {
        this.shimmerItemCount = count;
    }

    /**
     * @param shimmer other required Shimmer properties.
     */
    public void setShimmer(Shimmer shimmer) {
        this.settings = shimmer;
    }

    public void startShimmer() {
        // TODO: 3/8/19 switch adapters.
    }

    public void hideShimmer() {
        // TODO: 3/8/19 switch adapters.
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal APIs
    ///////////////////////////////////////////////////////////////////////////

    private void initialize(Context context, AttributeSet attrs) {
        mShimmerAdapter = new ShimmerAdapter(shimmerLayout, shimmerItemCount, settings);
        
    }
}
