package com.pinankh.android

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

enum class StopWatchState{
    START,PAUSE,RESET
}

class Main2ActivityViewModel: ViewModel(){
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _elapsedSeconds = MutableLiveData<Long>(0)
    val elapsedSeconds: LiveData<Long> = _elapsedSeconds

    private val _stopWatchStatus = MutableLiveData<StopWatchState>()
    val stopWatchState: LiveData<StopWatchState> = _stopWatchStatus

    fun startStopWatch(){
        uiScope.launch {
            while (true) {
                delay(1000)
                _elapsedSeconds.value = _elapsedSeconds.value?.plus(1)
                Log.d("BOSS_DK", elapsedSeconds.value.toString())
            }
        }
    }

    fun pauseStopWatch(){
        uiScope.launch {
            job.cancelAndJoin()
        }
    }

    fun resetStopWatch(){
        uiScope.cancel()
    }

    private fun timer(){

    }
}