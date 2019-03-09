package com.todkars.shimmer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.Shimmer.Direction;
import com.facebook.shimmer.Shimmer.Shape;

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
     * Setting {@link Shimmer} programmatically will ignore all xml properties.
     *
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
        if (settings == null) settings = getDefaultSettings(context, attrs);
        mShimmerAdapter = new ShimmerAdapter(shimmerLayout, shimmerItemCount, settings);
    }

    /**
     * Extract xml attributes from {@link ShimmerRecyclerView}.
     *
     * @param context {@link android.app.Activity} context...
     * @param attrs   view attributes
     * @return default {@link Shimmer} built-up considering xml attributes.
     */
    private Shimmer getDefaultSettings(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ShimmerRecyclerView, 0, 0);
        try {
            Shimmer.Builder builder = new Shimmer.ColorHighlightBuilder();
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_clip_to_children)) {
                builder.setClipToChildren(a.getBoolean(
                        R.styleable.ShimmerRecyclerView_shimmer_clip_to_children, true));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_auto_start)) {
                builder.setAutoStart(a.getBoolean(
                        R.styleable.ShimmerRecyclerView_shimmer_auto_start, true));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_base_alpha)) {
                builder.setBaseAlpha(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_base_alpha, 0.3f));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_highlight_alpha)) {
                builder.setHighlightAlpha(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_highlight_alpha, 1f));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_duration)) {
                builder.setDuration(a.getInteger(
                        R.styleable.ShimmerRecyclerView_shimmer_duration, 1000));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_repeat_count)) {
                builder.setRepeatCount(a.getInt(
                        R.styleable.ShimmerRecyclerView_shimmer_repeat_count, ValueAnimator.INFINITE));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_repeat_delay)) {
                builder.setRepeatDelay(a.getInt(
                        R.styleable.ShimmerRecyclerView_shimmer_repeat_delay, 0));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_repeat_mode)) {
                builder.setRepeatMode(a.getInt(
                        R.styleable.ShimmerRecyclerView_shimmer_repeat_mode, ValueAnimator.RESTART));
            }

            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_direction)) {
                int direction = a.getInt(
                        R.styleable.ShimmerRecyclerView_shimmer_direction, Direction.LEFT_TO_RIGHT);
                switch (direction) {
                    default:
                    case Direction.LEFT_TO_RIGHT:
                        builder.setDirection(Direction.LEFT_TO_RIGHT);
                        break;
                    case Direction.TOP_TO_BOTTOM:
                        builder.setDirection(Direction.TOP_TO_BOTTOM);
                        break;
                    case Direction.RIGHT_TO_LEFT:
                        builder.setDirection(Direction.RIGHT_TO_LEFT);
                        break;
                    case Direction.BOTTOM_TO_TOP:
                        builder.setDirection(Direction.BOTTOM_TO_TOP);
                        break;
                }
            }

            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_shape)) {
                int shape = a.getInt(R.styleable.ShimmerRecyclerView_shimmer_shape, Shape.LINEAR);
                switch (shape) {
                    default:
                    case Shape.LINEAR:
                        builder.setShape(Shape.LINEAR);
                        break;
                    case Shape.RADIAL:
                        builder.setShape(Shape.RADIAL);
                        break;
                }
            }

            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_fixed_width)) {
                builder.setFixedWidth(a.getDimensionPixelSize(
                        R.styleable.ShimmerRecyclerView_shimmer_fixed_width, 0));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_fixed_height)) {
                builder.setFixedHeight(a.getDimensionPixelSize(
                        R.styleable.ShimmerRecyclerView_shimmer_fixed_height, 0));
            }

            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_width_ratio)) {
                builder.setWidthRatio(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_width_ratio, 1f));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_height_ratio)) {
                builder.setHeightRatio(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_height_ratio, 1f));
            }

            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_intensity)) {
                builder.setIntensity(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_intensity, 0f));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_dropoff)) {
                builder.setDropoff(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_dropoff, 0.5f));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_tilt)) {
                builder.setTilt(a.getFloat(
                        R.styleable.ShimmerRecyclerView_shimmer_tilt, 20f));
            }

            return builder.build();
        } finally {
            a.recycle();
        }
    }
}
