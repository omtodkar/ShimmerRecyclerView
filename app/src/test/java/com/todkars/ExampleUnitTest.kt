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

import android.widget.Button
import android.widget.CheckBox
import androidx.recyclerview.widget.GridLayoutManager
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.todkars.ExampleTestActivity.TestCallback
import com.todkars.model.User
import com.todkars.shimmer.ShimmerAdapter
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.android.synthetic.main.activity_example.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private lateinit var controller: ActivityController<ExampleTestActivity>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        controller = Robolectric.buildActivity(ExampleTestActivity::class.java)
    }

    /**
     * Check if {@link UserRetrievalTask} is
     * called in {@link ExampleActivity}.
     */
    @Test
    fun test_user_retrieval_task_is_called_at_start() {
        // given
        val task = Mockito.mock(UserRetrievalTask::class.java)

        // when
        controller.get().userRetrievalTask = task

        // fast-forward controller to resumed & visible state.
        controller.setup()

        // then
        Mockito.verify(task).execute()
    }

    /**
     * Confirm {@link UserRetrievalTask} returns fix number
     * of users every time.
     */
    @Test
    fun test_user_retrieval_task_gives_same_amount_of_users() {
        // when
        val isNotNotified = AtomicBoolean(true)
        val obj = Object()
        controller.get().callback = object : TestCallback {
            override fun onResultReceived(users: List<User>) {
                // then
                assertThat(users.size).isEqualTo(1000)

                synchronized(obj) {
                    isNotNotified.compareAndSet(true, false)
                    obj.notify()
                }
            }
        }

        // given fast-forward controller to resumed & visible state.
        controller.setup()

        if (isNotNotified.get())
            synchronized(obj) {
                obj.wait()
            }
    }

    /**
     * Confirm {@link UserRetrievalTask} is called on click of reload button.
     */
    @Test
    fun test_on_reload_button_click_user_retrieval_task_is_called() {
        // given
        val isNotNotified = AtomicBoolean(true)
        val obj = Object()
        controller.get().callback = object : TestCallback {
            override fun onResultReceived(users: List<User>) {
                val task = Mockito.mock(UserRetrievalTask::class.java)

                // when
                controller.get().userRetrievalTask = task
                val reload = controller.get().findViewById<Button>(R.id.toggle_loading)
                reload.performClick()

                // then
                Mockito.verify(task).execute()

                synchronized(obj) {
                    isNotNotified.compareAndSet(true, false)
                    obj.notify()
                }
            }
        }

        // fast-forward controller to resumed & visible state.
        controller.setup()

        if (isNotNotified.get())
            synchronized(obj) {
                obj.wait()
            }
    }

    /**
     * Check on click of toggle button, shimmer is shown.
     */
    @Test
    fun test_toggle_shimmer_on_button_click() {
        // given
        val isNotNotified = AtomicBoolean(true)
        val obj = Object()
        controller.get().callback = object : TestCallback {
            override fun onResultReceived(users: List<User>) {
                val recyclerView = controller.get().user_listing
                val toggleButton = controller.get().toggle_shimmer

                // when
                toggleButton.performClick()

                // then
                assertThat(recyclerView.adapter is ShimmerAdapter).isTrue()

                synchronized(obj) {
                    isNotNotified.compareAndSet(true, false)
                    obj.notify()
                }
            }
        }

        // fast-forward controller to resumed & visible state.
        controller.setup()

        if (isNotNotified.get())
            synchronized(obj) {
                obj.wait()
            }
    }

    /**
     * Confirm initial {@link LayoutManager} of {@link ShimmerRecyclerView}
     * is always a {@link LinearLayoutManager} and on toggle twice it changes to
     *
     * 1. Grid (GridLayoutManager)
     * 2. List (LinearLayoutManager)
     */
    @Test
    fun test_on_change_layout_manager_shimmer_layout_manager_changes() {
        // given
        val isNotNotified = AtomicBoolean(true)
        val obj = Object()
        controller.get().callback = object : TestCallback {
            override fun onResultReceived(users: List<User>) {
                val recyclerView = controller.get()
                        .findViewById<ShimmerRecyclerView>(R.id.user_listing)

                val layoutToggleButton = controller.get()
                        .findViewById<CheckBox>(R.id.change_layout_orientation)

                // default state
                assertWithMessage("at default state: %s",
                        recyclerView.layoutManager?.javaClass?.name)
                        .that(layoutToggleButton.isChecked)
                        .isFalse()

                // when
                layoutToggleButton.performClick()

                // then
                assertWithMessage("after 1st [checkbox]: %s", layoutToggleButton.isChecked)
                        .that(layoutToggleButton.isChecked)
                        .isTrue()

                // when  workaround as data binding OnChangeListener not working as excepted.
                controller.get()
                        .onLayoutOrientationChange(layoutToggleButton, layoutToggleButton.isChecked)

                // and then
                assertWithMessage("after 1st click [layout-manager]: %s",
                        recyclerView.layoutManager?.javaClass?.name)
                        .that(recyclerView.layoutManager)
                        .isInstanceOf(GridLayoutManager::class.java)

                // and
                assertWithMessage("after 1st click [shimmer-layout-manager]: %s",
                        recyclerView.shimmerLayoutManager?.javaClass?.name)
                        .that(recyclerView.shimmerLayoutManager)
                        .isInstanceOf(GridLayoutManager::class.java)

                // again when
                layoutToggleButton.performClick()

                // then
                assertWithMessage("on 2nd [checkbox]: %s", layoutToggleButton.isChecked)
                        .that(layoutToggleButton.isChecked)
                        .isFalse()

                // when  workaround as data binding OnChangeListener not working as excepted.
                controller.get()
                        .onLayoutOrientationChange(layoutToggleButton, layoutToggleButton.isChecked)

                // and then
                assertWithMessage("on 2nd click [layout-manager]: %s",
                        recyclerView.layoutManager?.javaClass?.name)
                        .that(recyclerView.layoutManager)
                        .isNotInstanceOf(GridLayoutManager::class.java)

                // and
                assertWithMessage("on 2nd click [shimmer-layout-manager]: %s",
                        recyclerView.shimmerLayoutManager?.javaClass?.name)
                        .that(recyclerView.shimmerLayoutManager)
                        .isNotInstanceOf(GridLayoutManager::class.java)

                synchronized(obj) {
                    isNotNotified.compareAndSet(true, false)
                    obj.notify()
                }
            }
        }

        // fast-forward controller to resumed & visible state.
        controller.setup()

        if (isNotNotified.get())
            synchronized(obj) {
                obj.wait()
            }
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        controller.stop().destroy()
    }
}