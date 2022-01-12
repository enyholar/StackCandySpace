package com.gideondev.stackcandyspace.Utils

import android.app.Application
import com.gideondev.stackcandyspace.R

class StringUtils(val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)
}
