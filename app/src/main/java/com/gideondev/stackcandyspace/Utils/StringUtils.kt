package com.gideondev.stackcandyspace.Utils

import android.annotation.SuppressLint
import android.app.Application
import com.gideondev.stackcandyspace.R
import java.text.SimpleDateFormat
import java.util.*

class StringUtils(val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)

}




