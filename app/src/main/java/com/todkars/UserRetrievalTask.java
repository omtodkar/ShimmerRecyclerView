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

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todkars.model.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class UserRetrievalTask extends AsyncTask<Void, Void, List<User>> {
    private final WeakReference<Context> context;

    interface UserRetrievalResult {

        void onResult(List<User> users);
    }

    private final UserRetrievalResult result;

    UserRetrievalTask(Context context, UserRetrievalResult result) {
        this.context = new WeakReference<>(context.getApplicationContext());
        this.result = result;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        if (context.get() != null) {
            InputStream dataStream = context.get().getResources().openRawResource(R.raw.mock_data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
            List<User> users = new Gson().fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
            Collections.shuffle(users);
            return users;
        } else return Collections.emptyList();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        if (result != null) result.onResult(users);
    }

}
