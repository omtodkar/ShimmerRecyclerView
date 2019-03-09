package com.todkars.shimmer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.Shimmer.Direction;
import com.facebook.shimmer.Shimmer.Shape;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public final class ShimmerRecyclerView extends RecyclerView {

    private ShimmerAdapter mShimmerAdapter;

    private Adapter mActualAdapter;

    private LayoutManager mShimmerLayoutManager;

    private LayoutManager mLayoutManager;

    private boolean mScrollEnabled;

    @RecyclerView.Orientation
    private int mLayoutOrientation = RecyclerView.VERTICAL;

    private boolean mLayoutReverse = false;

    private int mGridSpanCount = -1;

    @LayoutRes
    private int mShimmerLayout;

    private int mShimmerItemCount;

    private Shimmer shimmer;

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

    @CallSuper
    @Override
    public void setLayoutManager(@Nullable LayoutManager manager) {
        if (manager == null) {
            mLayoutManager = null;
        } else if (manager != mShimmerLayoutManager) {
            if (manager instanceof GridLayoutManager) {
                mGridSpanCount = ((GridLayoutManager) manager).getSpanCount();
            }

            if (manager instanceof LinearLayoutManager) {
                mLayoutReverse = ((LinearLayoutManager) manager).getReverseLayout();
                mLayoutOrientation = ((LinearLayoutManager) manager).getOrientation();
            }

            mLayoutManager = manager;
        }

        initializeLayoutManager();

        if (mShimmerAdapter != null) {
            mShimmerAdapter.setLayout(mShimmerLayout);
            mShimmerAdapter.setCount(mShimmerItemCount);
            mShimmerAdapter.setShimmer(shimmer);

            mShimmerAdapter.notifyDataSetChanged();
        }

        super.setLayoutManager(manager);
    }

    @CallSuper
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter == null) {
            mActualAdapter = null;
        } else if (adapter != mShimmerAdapter) {
            mActualAdapter = adapter;
        }

        super.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public APIs
    ///////////////////////////////////////////////////////////////////////////

    public final void showShimmer() {
        mScrollEnabled = false;

        if (mShimmerLayoutManager == null) {
            initializeLayoutManager();
        }

        setLayoutManager(mShimmerLayoutManager);
        setAdapter(mShimmerAdapter);
    }

    public final void hideShimmer() {
        mScrollEnabled = true;

        setLayoutManager(mLayoutManager);
        setAdapter(mActualAdapter);
    }

    /**
     * @param layout layout reference for shimmer adapter.
     */
    public final void setShimmerLayout(@LayoutRes int layout) {
        this.mShimmerLayout = layout;
    }

    /**
     * @return layout reference used as shimmer layout.
     */
    public final int getShimmerLayout() {
        return mShimmerLayout;
    }

    /**
     * @param count Number of items to be shown in shimmer adapter.
     */
    public final void setShimmerItemCount(int count) {
        this.mShimmerItemCount = count;
    }

    /**
     * @return number of items shown in shimmer adapter.
     */
    public final int getShimmerItemCount() {
        return mShimmerItemCount;
    }

    /**
     * @param manager Shimmer {@link androidx.recyclerview.widget.RecyclerView.LayoutManager}
     */
    public final void setShimmerLayoutManager(@NonNull LayoutManager manager) {
        this.mShimmerLayoutManager = manager;
    }

    /**
     * @return {@link androidx.recyclerview.widget.RecyclerView.LayoutManager} used for shimmer adapter.
     */
    public final LayoutManager getShimmerLayoutManager() {
        return mShimmerLayoutManager;
    }

    /**
     * Setting {@link Shimmer} programmatically will ignore all xml properties.
     *
     * @param shimmer other required Shimmer properties.
     */
    public final void setShimmer(Shimmer shimmer) {
        this.shimmer = shimmer;
    }

    /**
     * @return current {@link Shimmer}
     */
    public final Shimmer getShimmer() {
        return shimmer;
    }

    /**
     * @return Shimmer adapter
     */
    public final Adapter getShimmerAdapter() {
        return mShimmerAdapter;
    }

    /**
     * @return Actual adapter
     */
    public final Adapter getActualAdapter() {
        return mActualAdapter;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal APIs
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Initialize Shimmer adapter based on provided shimmer settings.
     *
     * @param context application context.
     * @param attrs   nullable attribute set.
     */
    private void initialize(Context context, AttributeSet attrs) {
        if (shimmer == null) shimmer = getDefaultSettings(context, attrs);
        mShimmerAdapter = new ShimmerAdapter(mShimmerLayout, mShimmerItemCount, shimmer);
    }

    /**
     * Based on actual layout manager,
     * prepare shimmer layout manager.
     */
    private void initializeLayoutManager() {
        if (mGridSpanCount >= 0) {
            mShimmerLayoutManager = new GridLayoutManager(getContext(), mGridSpanCount) {
                @Override
                public boolean canScrollVertically() {
                    return mScrollEnabled;
                }
            };
        } else {
            mShimmerLayoutManager = new LinearLayoutManager(getContext(),
                    mLayoutOrientation, mLayoutReverse) {
                @Override
                public boolean canScrollVertically() {
                    return mScrollEnabled;
                }

                @Override
                public boolean canScrollHorizontally() {
                    return mScrollEnabled;
                }
            };
        }
    }

    /**
     * Extract xml attributes from {@link ShimmerRecyclerView}.
     *
     * @param context {@link android.app.Activity} context...
     * @param attrs   view attributes
     * @return default {@link Shimmer} built-up considering xml attributes.
     */
    private Shimmer getDefaultSettings(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return new Shimmer.AlphaHighlightBuilder().build();
        }

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ShimmerRecyclerView, 0, 0);

        try {
            Shimmer.Builder builder = a.hasValue(R.styleable.ShimmerFrameLayout_shimmer_colored)
                    && a.getBoolean(R.styleable.ShimmerFrameLayout_shimmer_colored, false)
                    ? new Shimmer.ColorHighlightBuilder()
                    : new Shimmer.AlphaHighlightBuilder();

            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_clip_to_children)) {
                builder.setClipToChildren(a.getBoolean(
                        R.styleable.ShimmerRecyclerView_shimmer_clip_to_children, true));
            }
            if (a.hasValue(R.styleable.ShimmerRecyclerView_shimmer_auto_start)) {
                builder.setAutoStart(a.getBoolean(
                        R.styleable.ShimmerRecyclerView_shimmer_auto_start, true));
            }

            if (a.hasValue(R.styleable.ShimmerFrameLayout_shimmer_base_color)
                    && builder instanceof Shimmer.ColorHighlightBuilder) {
                ((Shimmer.ColorHighlightBuilder) builder).setBaseColor(a.getColor(
                        R.styleable.ShimmerFrameLayout_shimmer_base_color, 0x4cffffff));
            }

            if (a.hasValue(R.styleable.ShimmerFrameLayout_shimmer_highlight_color)
                    && builder instanceof Shimmer.ColorHighlightBuilder) {
                ((Shimmer.ColorHighlightBuilder) builder).setHighlightColor(a.getColor(
                        R.styleable.ShimmerFrameLayout_shimmer_highlight_color, Color.WHITE));
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
