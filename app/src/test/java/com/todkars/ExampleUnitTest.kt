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
import com.todkars.ExampleTestActivity.TestCallback
import com.todkars.model.User
import com.todkars.shimmer.ShimmerAdapter
import kotlinx.android.synthetic.main.activity_example.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.shadows.ShadowApplication
import java.util.concurrent.atomic.AtomicInteger

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private var activity: ActivityController<ExampleTestActivity>? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        activity = Robolectric.buildActivity(ExampleTestActivity::class.java)
    }

    /**
     * Check if {@link UserRetrievalTask} is
     * called in {@link ExampleActivity}.
     */
    @Test
    fun test_user_retrieval_task_is_called() {
        // given
        val task = Mockito.mock(UserRetrievalTask::class.java)

        // when
        activity?.get()?.userRetrievalTask = task
        activity?.setup()

        // then
        Mockito.verify(task).execute()
    }

    /**
     * Confirm {@link UserRetrievalTask} returns fix number
     * of users every time.
     */
    @Test
    fun test_user_retrieval_task_gives_same_amount_of_users() {
        // given
        activity?.setup() // fast-forward activity to resumed & visible state.

        val users = ArrayList<User>()   // prepare async task.
        val task = UserRetrievalTask(activity?.get(), UserRetrievalTask
                .UserRetrievalResult { users.addAll(it) })
        task.execute()

        // when
        ShadowApplication.runBackgroundTasks()

        // then
        Assert.assertEquals(1000, users.size)
    }

    /**
     * Confirm initial {@link LayoutManager} of {@link ShimmerRecyclerView}
     * is always a {@link LinearLayoutManager}
     */
    @Test
    fun test_initial_layout_manager_is_linear_layout_manager() {
        // given
        activity?.setup() // fast-forward activity to resumed & visible state.

        // when
        val recyclerView = activity?.get()?.user_listing
        val checkbox = activity?.get()?.change_layout_orientation

        // then
        Assert.assertTrue(!checkbox!!.isChecked)
        Assert.assertTrue(recyclerView?.layoutManager !is GridLayoutManager)
    }

    /**
     * Check on click of toggle button, shimmer is shown.
     */
    @Test
    fun test_toggle_shimmer_on_button_click() {
        // given
        val isNotifiedCounter = AtomicInteger(0)

        // set a callback for 'when' expressions and 'then' assertion
        // so it is called after all concurrent work completes.
        val obj = Object()
        activity?.get()?.callback = object : TestCallback {
            override fun onResultReceived(users: List<User>) {
                val recyclerView = activity?.get()?.user_listing
                val toggleButton = activity?.get()?.toggle_shimmer

                // when
                toggleButton?.performClick()

                // then
                Assert.assertTrue(recyclerView?.adapter is ShimmerAdapter)

                synchronized(obj) {
                    isNotifiedCounter.incrementAndGet()
                    obj.notify()
                }
            }
        }

        // fast-forward activity to resumed & visible state.
        activity?.setup()

        // wait only if result callback is not called.
        if (isNotifiedCounter.get() == 0)
            synchronized(obj) {
                obj.wait()
            }
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        activity = null
    }
}