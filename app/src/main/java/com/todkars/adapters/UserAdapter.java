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
        this.notifyDataSetChanged();
    }
}
