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
package com.todkars

import androidx.recyclerview.widget.GridLayoutManager
import com.todkars.model.User
import kotlinx.android.synthetic.main.activity_example.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.shadows.ShadowApplication

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private lateinit var activity: ActivityController<ExampleActivity>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        activity = Robolectric.buildActivity(ExampleActivity::class.java)
    }

    /**
     * fast-forward activity to resumed state.
     */
    private fun fastForwardActivityLifecycle() {
        activity.create().start().resume()
    }

    @Test
    fun test_user_retrieval_task_give_same_amount_of_users() {
        // given
        fastForwardActivityLifecycle()

        val users = ArrayList<User>()   // prepare async task.
        val task = UserRetrievalTask(activity.get(), UserRetrievalTask
                .UserRetrievalResult { users.addAll(it) })
        task.execute()

        // when
        ShadowApplication.runBackgroundTasks()

        // then
        Assert.assertEquals(1000, users.size)
    }

    @Test
    fun test_initial_layout_manager_is_linear_layout_manager() {
        // given
        fastForwardActivityLifecycle()

        // when
        val recyclerView = activity.get().user_listing

        // then
        Assert.assertTrue(recyclerView.layoutManager !is GridLayoutManager)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }
}