# ShimmerRecyclerView

![Bintray](https://img.shields.io/bintray/v/todkars/android/shimmer-recyclerview.svg?color=%230288D1&label=Version) ![GitHub](https://img.shields.io/github/license/omtodkar/ShimmerRecyclerView.svg?label=License) [![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

ShimmerRecyclerView is an custom RecyclerView library based on Facebook's [Shimmer](https://github.com/facebook/shimmer-android) effect for Android library and inspired from [Sharish's ShimmerRecyclerView](https://github.com/sharish/ShimmerRecyclerView).

There is reason behind creating a separate library for ShimmerRecyclerView, most of libraries doesn't not support runtime switching of `LayoutManager` or shimmer `layout` resources. Secondly the other similar library is build upon [Supercharge's ShimmerLayout](https://github.com/team-supercharge/ShimmerLayout) which is I feel very less active in terms of release. So I came up with an alternative.

## Download

To include `ShimmerRecyclerView` in your project, add the following to your dependencies:

**app/build.gradle**
```groovy
dependencies {
    implementation 'com.todkars:shimmer-recyclerview:{latest-version}'
}
```

## Usage
The following snippet shows how you can use Shimmer RecyclerView in your project.

**Layout** 

```xml
<com.todkars.shimmer.ShimmerRecyclerView
    android:id="@+id/shimmer_recycler_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:shimmer_recycler_colored="true"
    app:shimmer_recycler_duration="1000" />
```

**Activity**

```java
public class MainActivity extends Activity {
    
    private ShimmerRecyclerView mShimmerRecyclerView;
    
    //... other variables
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mShimmerRecyclerView = findViewById(R.id.shimmer_recycler_view);
        mShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShimmerRecyclerView.setAdapter(adapter);
        
        // This is optional, use if no attributes are mentioned in layout xml resource.
        // WARNING: Setting Shimmer programmatically will obsolete all xml attributes.
        /* mShimmerRecyclerView.setShimmer(mShimmer); */
        
        mShimmerRecyclerView.showShimmer();     // to start showing shimmer
        
        // To stimulate long running work using android.os.Handler
        mHandler.postDelayed((Runnable) () -> {
            mShimmerRecyclerView.hideShimmer(); // to hide shimmer
        }, 3000);
    }
}
``` 

## Attributes

All attributes used for **ShimmerRecyclerView** are same as Facebook's ***ShimmerFrameLayout*** below is detail table:

| Name | Attribute |  Description |
|---|---|---|
| Clip to Children | `shimmer_recycler_clip_to_children` | Whether to clip the shimmering effect to the children, or to opaquely draw the shimmer on top of the children. Use this if your overall layout contains transparency. |
| Colored | `shimmer_recycler_colored` | Whether you want the shimmer to affect just the alpha or draw colors on-top of the children. |
| Base Color | `shimmer_recycler_base_color` | If colored is specified, the base color of the content. |
| Highlight Color | `shimmer_recycler_highlight_color` | If colored is specified, the shimmer's highlight color. |
| Base Alpha | `shimmer_recycler_base_alpha` | If colored is not specified, the alpha used to render the base view i.e. the unhighlighted view over which the highlight is drawn. |
| Highlight Alpha | `shimmer_recycler_highlight_alpha` | If colored is not specified, the alpha of the shimmer highlight. |
| Auto Start | `shimmer_recycler_auto_start` | Whether to automatically start the animation when the view is shown, or not. |
| Duration | `shimmer_recycler_duration` | Time it takes for the highlight to move from one end of the layout to the other. |
| Repeat Count | `shimmer_recycler_repeat_count` | Number of times of the current animation will repeat. |
| Repeat Delay | `shimmer_recycler_repeat_delay` | Delay after which the current animation will repeat. |
| Repeat Mode | `shimmer_recycler_repeat_mode` | What the animation should do after reaching the end, either restart from the beginning or reverse back towards it. |
| Direction | `shimmer_recycler_direction` |The travel direction of the shimmer highlight: left to right, top to bottom, right to left or bottom to top. |
| Dropoff | `shimmer_recycler_dropoff` | Controls the size of the fading edge of the highlight. |
| Intensity | `shimmer_recycler_intensity` | Controls the brightness of the highlight at the center |
| Shape | `shimmer_recycler_shape` | Shape of the highlight mask, either with a linear or a circular gradient. |
| Tilt | `shimmer_recycler_tilt` | Angle at which the highlight is tilted, measured in degrees. |
| Fixed Width or Height | `shimmer_recycler_fixed_width` or `shimmer_recycler_fixed_height` | Fixed sized highlight mask, if set, overrides the relative size value. |
| Width or Height Ratio | `shimmer_recycler_width_ratio` or `shimmer_recycler_height_ratio` | Size of the highlight mask, relative to the layout it is applied to. |

## [LICENSE](https://github.com/omtodkar/ShimmerRecyclerView/blob/master/LICENSE.md)

```text
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
    along with this program.  If not, see https://www.gnu.org/licenses/gpl.txt
```
