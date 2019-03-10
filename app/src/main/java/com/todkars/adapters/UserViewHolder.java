package com.todkars.adapters;

import com.todkars.BR;
import com.todkars.model.User;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

class UserViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binder;

    UserViewHolder(ViewDataBinding binder) {
        super(binder.getRoot());
        this.binder = binder;
    }

    void bind(User user) {
        binder.setVariable(BR.user, user);
        binder.executePendingBindings();
    }
}
