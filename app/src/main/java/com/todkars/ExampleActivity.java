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
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.todkars.adapters.UserAdapter;
import com.todkars.databinding.ActivityExampleBinding;
import com.todkars.model.User;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.List;

public class ExampleActivity extends AppCompatActivity
        implements UserRetrievalTask.UserRetrievalResult {

    public UserRetrievalTask userRetrievalTask;

    private boolean buttonsEnabled = true;

    private UserAdapter adapter;

    public ActivityExampleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExampleBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        /* initialize empty adapter */
        adapter = new UserAdapter();

        setupViews();
    }

    /**
     * Initial view and recycler list setup.
     */
    private void setupViews() {
        binding.setVariable(BR.activity, this);
        binding.setVariable(BR.active, buttonsEnabled);
        binding.toggleLoading.setOnClickListener(this::onReload);
        binding.toggleShimmer.setOnClickListener(this::onToggleShimmer);
        binding.userListing.setItemViewType((type, position) -> {
            switch (type) {
                case ShimmerRecyclerView.LAYOUT_GRID:
                    return position % 2 == 0
                            ? R.layout.list_item_shimmer_grid
                            : R.layout.list_item_shimmer_grid_alternate;

                default:
                case ShimmerRecyclerView.LAYOUT_LIST:
                    return position % 2 == 0
                            ? R.layout.list_item_shimmer
                            : R.layout.list_item_shimmer_alternate;
            }
        });
        binding.userListing.setAdapter(adapter);

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
        binding.userListing.setLayoutManager(
                isGrid
                        ? new GridLayoutManager(this, 2)
                        : new LinearLayoutManager(this),
                isGrid
                        ? R.layout.list_item_shimmer_grid
                        : R.layout.list_item_shimmer);
        adapter.changeOrientation(isGrid);
        binding.userListing.setAdapter(adapter);
    }

    /**
     * Toggle show / hide shimmer on list.
     *
     * @param view toggle button view.
     */
    public void onToggleShimmer(View view) {
        if (binding.userListing.isShimmerShowing()) {
            binding.userListing.hideShimmer();
            ((Button) view).setText(R.string.toggle_shimmer_show);
        } else {
            binding.userListing.showShimmer();
            ((Button) view).setText(R.string.toggle_shimmer_hide);
        }

        binding.changeLayoutOrientation.setEnabled(!binding.userListing.isShimmerShowing());
        binding.toggleLoading.setEnabled(!binding.userListing.isShimmerShowing());
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

        binding.userListing.showShimmer();
        binding.changeLayoutOrientation.setEnabled(false);
        binding.toggleShimmer.setEnabled(false);
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

        binding.userListing.hideShimmer();
        binding.changeLayoutOrientation.setEnabled(true);
        binding.toggleShimmer.setEnabled(true);

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
