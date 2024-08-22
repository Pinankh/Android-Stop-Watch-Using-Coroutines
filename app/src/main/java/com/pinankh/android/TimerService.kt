package com.pinankh.android

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*


class TimerService : Service() {
    private val binder = LocalBinder()

    private val _elapsedSeconds = MutableLiveData<Long>(0)
    val elapsedSeconds: LiveData<Long> = _elapsedSeconds

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        job.start()
        uiScope.launch {
            while (true) {
                delay(1000)
                _elapsedSeconds.value = _elapsedSeconds.value?.plus(1)
                addNotification(intent,formatStopWatchTime(_elapsedSeconds.value))
            }
        }

        return START_NOT_STICKY
    }

    private fun addNotification(intent: Intent, message: String){
        //val input = intent.getStringExtra("inputExtra")
        val notificationIntent = Intent(this, Main2Activity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(SERVICE_NAME)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_android)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(SERVICE_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }
}