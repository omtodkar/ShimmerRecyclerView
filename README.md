# Shimmer RecyclerView ![GPL v3](https://www.gnu.org/graphics/gplv3-88x31.png)

![Version](https://api.bintray.com/packages/todkars/android/shimmer-recyclerview/images/download.svg?version=0.1.0)

Shimmer RecyclerView is an custom RecyclerView library based on Facebook's [Shimmer](https://github.com/facebook/shimmer-android) effect for Android library and inspired from [Sharish's ShimmerRecyclerView](https://github.com/sharish/ShimmerRecyclerView).

There is reason behind creating a separate library for ShimmerRecyclerView, most of libraries doesn't not support runtime switching of `LayoutManager` or shimmer `layout` resource. Secondly [Sharish's ShimmerRecyclerView](https://github.com/sharish/ShimmerRecyclerView) is build upon [Supercharge's ShimmerLayout](https://github.com/team-supercharge/ShimmerLayout) which is I feel very less active in terms of release. So I came up with an alternative. 

Happy Coding!

## Usage
The following snippet shows how you can use Shimmer RecyclerView in your project.

```
    <com.todkars.shimmer.ShimmerRecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

## Attributes

All attributes are same as Facebook's ***ShimmerFrameLayout*** as follows:

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

## Download

To include Shimmer RecyclerView in your project, add the following dependency:

**{project}/build.gradle**
```
buildscript {
    repositories {
        google()
        jcenter()
        maven { url  "https://dl.bintray.com/todkars/android" }
    }
}
```

**app/build.gradle**
```
dependencies {
    implementation 'com.todkars:shimmer-recyclerview:{latest-version}'
}
```

## [LICENSE](../blob/master/LICENSE.md)

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
