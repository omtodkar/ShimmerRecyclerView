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
package com.todkars.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.todkars.R;
import com.todkars.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private boolean grid = false;

    private List<User> users;

    public UserAdapter() {
        this.users = Collections.emptyList();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), grid
                ? R.layout.list_item_grid_user : R.layout.list_item_vertical_user, parent, false);
        return new UserViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public void updateData(List<User> users) {
        this.users = new ArrayList<>(users);
        notifyDataSetChanged();
    }

    /**
     * On changing layout orientation.
     *
     * @param grid change to list or grid layout.
     */
    public void changeOrientation(boolean grid) {
        this.grid = grid;
    }
}
