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

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.todkars.adapters.UserAdapter;
import com.todkars.model.User;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.List;

public class ExampleActivity extends AppCompatActivity implements UserRetrievalTask.UserRetrievalResult {

    @VisibleForTesting
    public UserRetrievalTask userRetrievalTask;

    private UserAdapter adapter = new UserAdapter();

    private Button mToggleButton;
    private ShimmerRecyclerView mShimmerRecyclerView;

    private boolean buttonsEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        setupViews();
    }

    /**
     * Initial view and recycler list setup.
     */
    private void setupViews() {
        if (userRetrievalTask == null)
            userRetrievalTask = new UserRetrievalTask(ExampleActivity.this,
                    ExampleActivity.this);

        ViewDataBinding binder = DataBindingUtil.setContentView(this, R.layout.activity_example);
        binder.setVariable(BR.activity, this);
        binder.setVariable(BR.active, buttonsEnabled);

        mToggleButton = binder.getRoot().findViewById(R.id.toggle_shimmer);

        mShimmerRecyclerView = binder.getRoot().findViewById(R.id.user_listing);
        mShimmerRecyclerView.setAdapter(adapter);
        mShimmerRecyclerView.setShimmerLayout(R.layout.list_item_vertical_shimmer);
        mShimmerRecyclerView.setShimmerItemCount(6);

        mShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        onReload(null);
    }

    /**
     * When layout orientation {@link android.widget.CheckBox} is checked,
     * set list orientation to gird else vice versa.
     *
     * @param button checkbox.
     * @param grid   isChecked value.
     */
    public void onLayoutOrientationChange(CompoundButton button, boolean grid) {
        RecyclerView.LayoutManager manager;
        if (grid) {
            mShimmerRecyclerView.setShimmerLayout(R.layout.list_item_grid_shimmer);
            manager = new GridLayoutManager(this, 2);
        } else {
            mShimmerRecyclerView.setShimmerLayout(R.layout.list_item_vertical_shimmer);
            manager = new LinearLayoutManager(this);
        }

        mShimmerRecyclerView.setLayoutManager(manager);
        adapter.changeOrientation(grid);
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
    }

    /**
     * Reload and shuffle data,
     * show shimmer while loading.
     *
     * @param view button view.
     */
    public void onReload(View view) {
        buttonsEnabled = false;
        mShimmerRecyclerView.showShimmer();
        mToggleButton.setVisibility(View.INVISIBLE);
        userRetrievalTask.execute();
    }

    /**
     * On result of {@link UserRetrievalTask} this method is
     * called with users data.
     *
     * @param users list of {@link User}s
     */
    @Override
    @VisibleForTesting
    public void onResult(List<User> users) {
        adapter.updateData(users);
        mShimmerRecyclerView.hideShimmer();
        buttonsEnabled = true;
        mToggleButton.setVisibility(View.VISIBLE);
    }
}
