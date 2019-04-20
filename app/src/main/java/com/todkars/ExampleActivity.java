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
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

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

public class ExampleActivity extends AppCompatActivity {

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
     * Initial setup.
     */
    private void setupViews() {
        ViewDataBinding binder = DataBindingUtil.setContentView(this, R.layout.activity_example);
        binder.setVariable(BR.activity, this);
        binder.setVariable(BR.active, buttonsEnabled);

        mToggleButton = binder.getRoot().findViewById(R.id.toggle_shimmer);

        mShimmerRecyclerView = binder.getRoot().findViewById(R.id.user_listing);
        mShimmerRecyclerView.setAdapter(adapter);
        mShimmerRecyclerView.setShimmerLayout(R.layout.list_item_vertical_shimmer);
        mShimmerRecyclerView.setShimmerItemCount(6);

        mShimmerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        onLoading(null);
    }

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
     * Show shimmer for loading.
     *
     * @param view button view.
     */
    public void onLoading(View view) {
        buttonsEnabled = false;
        mShimmerRecyclerView.showShimmer();
        mToggleButton.setVisibility(View.INVISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new UserRetrievalTask(ExampleActivity.this, new UserRetrievalTask.UserRetrievalResult() {
                    @Override
                    public void onResult(List<User> users) {
                        adapter.updateData(users);
                        mShimmerRecyclerView.hideShimmer();
                        buttonsEnabled = true;
                        mToggleButton.setVisibility(View.VISIBLE);
                    }
                }).execute();
            }
        }, 3000);
    }

    public void toggleShimmer(View view) {
        if (mShimmerRecyclerView.isShimmerShowing()) {
            mShimmerRecyclerView.hideShimmer();
            ((Button) view).setText(R.string.toggle_shimmer_show);
        } else {
            mShimmerRecyclerView.showShimmer();
            ((Button) view).setText(R.string.toggle_shimmer_hide);
        }
    }
}
