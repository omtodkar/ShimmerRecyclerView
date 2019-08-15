# ShimmerRecyclerView

ShimmerRecyclerView is an custom RecyclerView library based on Facebook's [Shimmer](https://github.com/facebook/shimmer-android) effect for Android library


### v0.4.0
- Linear Horizontal Recyclerview bug fixed.

### v0.3.0
Now you can set xml attributes for shimmer layout reference and number of shimmer to be shown while loading. Also now you can able to change view type of `ShimmerAdapter`.

- New `ItemViewType` contract to setup viewType for `ShimmerAdapter` based on `layoutType` and `position`, use `ShimmerRecyclerView.setItemViewType`.
- Use `shimmer_recycler_layout` xml attribute to set shimmer layout reference.
- Use `shimmer_recycler_item_count` xml attribute to set number of shimmer items.

### v0.2.0

Bug fixes and minor changes.

- Now use `setLayoutManager(androidx.recyclerview.widget.RecyclerView.LayoutManager, int)` to setup layout manager and shimmer layout resource together.
- `com.todkars.shimmer.ShimmerAdapter` is now accessible.
- Added support for Vertical / Horizontal `GridLayoutManager`.

### v0.1.0

Pre release.

- use `showShimmer` and `hideShimmer` to toggle shimmer view.
- Auto-detect layout type and orientation while showing shimmer.
- Setup own Shimmer using `ShimmerRecyclerView#setShimmer(com.facebook.shimmer.Shimmer)` to customize for ShimmerRecyclerView.
- All xml attributes are similar as facebook `ShimmerLayout`.
