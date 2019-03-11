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
