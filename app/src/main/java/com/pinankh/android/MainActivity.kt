package com.pinankh.android

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pinankh.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isStart = false
    private var elapsedMillis: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chronometer.setOnChronometerTickListener {
            startService1(it.text.toString())
        }

        binding.btnStartPause.setOnClickListener {
            if (isStart) {
                binding.chronometer.stop()
                isStart = false
                elapsedMillis =  binding.chronometer.getBase() - SystemClock.elapsedRealtime()
                if(elapsedMillis.equals(0)) {
                    (it as Button).text = "Start"
                }else{
                    (it as Button).text = "Resume"
                }
            } else {
                binding.chronometer.setBase(SystemClock.elapsedRealtime() + elapsedMillis)
                binding.chronometer.start()
                isStart = true
                (it as Button).text = "Pause"
            }
        }

        binding.btnReset.setOnClickListener {
            binding.chronometer.setBase(SystemClock.elapsedRealtime())
            elapsedMillis = 0
            binding.chronometer.stop()
            isStart = false
            binding.btnStartPause.text = "Start"
            stopService1()
        }
    }

    fun startService1(message: String) {
        val serviceIntent = Intent(this, TimerService::class.java)
        serviceIntent.putExtra("inputExtra", message)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    fun stopService1() {
        val serviceIntent = Intent(this, TimerService::class.java)
        stopService(serviceIntent)
    }
}
