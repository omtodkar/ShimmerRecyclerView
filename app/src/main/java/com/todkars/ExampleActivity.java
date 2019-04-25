/*
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
along with this program.  If not, see below link
https://github.com/omtodkar/ShimmerRecyclerView/blob/master/LICENSE.md
*/
package com.todkars;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.todkars.adapters.UserAdapter;
import com.todkars.model.User;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.List;

public class ExampleActivity extends AppCompatActivity
        implements UserRetrievalTask.UserRetrievalResult {

    public UserRetrievalTask userRetrievalTask;

    private Button mReloadButton;

    private Button mToggleButton;

    private CheckBox mOrientationButton;

    private ShimmerRecyclerView mShimmerRecyclerView;

    private boolean buttonsEnabled = true;

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        /* initialize empty adapter */
        adapter = new UserAdapter();

        setupViews();
    }

    /**
     * Initial view and recycler list setup.
     */
    private void setupViews() {
        ViewDataBinding binder = DataBindingUtil.setContentView(this,
                R.layout.activity_example);
        binder.setVariable(BR.activity, this);
        binder.setVariable(BR.active, buttonsEnabled);

        mReloadButton = binder.getRoot().findViewById(R.id.toggle_loading);
        mToggleButton = binder.getRoot().findViewById(R.id.toggle_shimmer);
        mOrientationButton = binder.getRoot().findViewById(R.id.change_layout_orientation);
        mShimmerRecyclerView = binder.getRoot().findViewById(R.id.user_listing);
        mShimmerRecyclerView.setAdapter(adapter);

        onReload(null /* initial call to load data */);
    }

    /**
     * When layout orientation {@link android.widget.CheckBox} is checked,
     * set list orientation to gird else vice versa.
     *
     * @param button view.
     * @param isGrid isChecked value.
     */
    public void onLayoutOrientationChange(CompoundButton button, boolean isGrid) {
        mShimmerRecyclerView.setLayoutManager(
                isGrid
                        ? new GridLayoutManager(this, 2)
                        : new LinearLayoutManager(this),
                isGrid
                        ? R.layout.list_item_shimmer_grid
                        : R.layout.list_item_shimmer);
        adapter.changeOrientation(isGrid);
        mShimmerRecyclerView.setAdapter(adapter);
    }

    /**
     * Toggle show / hide shimmer on list.
     *
     * @param view toggle button view.
     */
    public void onToggleShimmer(View view) {
        if (mShimmerRecyclerView.isShimmerShowing()) {
            mShimmerRecyclerView.hideShimmer();
            ((Button) view).setText(R.string.toggle_shimmer_show);
        } else {
            mShimmerRecyclerView.showShimmer();
            ((Button) view).setText(R.string.toggle_shimmer_hide);
        }

        mOrientationButton.setEnabled(!mShimmerRecyclerView.isShimmerShowing());
        mReloadButton.setEnabled(!mShimmerRecyclerView.isShimmerShowing());
    }

    /**
     * Reload and shuffle data,
     * show shimmer while loading.
     *
     * @param view button view.
     */
    public void onReload(View view) {
        buttonsEnabled = false;

        generateUserRetrievalTask().execute();

        mShimmerRecyclerView.showShimmer();
        mOrientationButton.setEnabled(false);
        mToggleButton.setEnabled(false);
    }

    /**
     * On result of {@link UserRetrievalTask} this method is
     * called with users data.
     *
     * @param users list of {@link User}s
     */
    @Override
    public void onResult(List<User> users) {
        adapter.updateData(users);

        mShimmerRecyclerView.hideShimmer();
        mOrientationButton.setEnabled(true);
        mToggleButton.setEnabled(true);

        buttonsEnabled = true;
    }

    /**
     * Generate new instance of {@link AsyncTask}
     * if it is not instantiated or already finished.
     *
     * @return instance of {@link AsyncTask}.
     */
    private UserRetrievalTask generateUserRetrievalTask() {
        if (userRetrievalTask == null || userRetrievalTask.getStatus() == AsyncTask.Status.FINISHED)
            userRetrievalTask = new UserRetrievalTask(this, this);
        return userRetrievalTask;
    }
}
