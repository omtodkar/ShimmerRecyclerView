# Shimmer RecyclerView ![GPL v3](https://www.gnu.org/graphics/gplv3-88x31.png)

![Version](https://api.bintray.com/packages/todkars/android/shimmer-recyclerview/images/download.svg?version=0.1.0)

A custom RecyclerView based on Facebook's [android-shimmer](https://github.com/facebook/shimmer-android) and inspired from Sharish's [ShimmerRecyclerView](https://github.com/sharish/ShimmerRecyclerView).

## How to add in your project?

*{project}/build.gradle*
```
buildscript {
    repositories {
        google()
        jcenter()
        maven { url  "https://dl.bintray.com/todkars/android" }
    }
}
```

*app/build.gradle*
```
dependencies {
    implementation 'com.todkars:shimmer-recyclerview:{latest-version}'
}
```
