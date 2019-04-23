package com.todkars

import com.todkars.model.User

class ExampleTestActivity : ExampleActivity() {

    // For test purpose only.
    interface TestCallback {

        fun onResultReceived(users: List<User>)
    }

    var callback: TestCallback? = null

    // notify test cases when async task returns result.
    override fun onResult(users: MutableList<User>?) {
        super.onResult(users)
        callback?.onResultReceived(users?.toList()!!)
    }
}