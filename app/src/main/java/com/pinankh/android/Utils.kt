package com.pinankh.android

const val CHANNEL_ID = "timerServiceChannel"
const val CHANNEL_NAME = "Timer Service Channel"
const val SERVICE_ID = 39862
const val SERVICE_NAME = "Timer Service"

fun formatStopWatchTime(seconds: Long?): String{
    seconds?.let {
        val hours: Long = seconds / 3600
        val minutes: Long = seconds % 3600 / 60
        val secs: Long = seconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
    return ""
}