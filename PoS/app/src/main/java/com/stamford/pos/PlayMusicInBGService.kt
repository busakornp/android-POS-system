package com.stamford.pos

import android.app.Service
import android.app.Service.START_STICKY
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.widget.Toast
import android.os.Process


class PlayMusicInBGService : Service() {
    private var playStatus : Boolean = false
    private var mPlayer: MediaPlayer? = null
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper){
        override fun handleMessage(msg: Message){
            try {
                Toast.makeText(this@PlayMusicInBGService,"Service created", Toast.LENGTH_SHORT).show()
                if (!playStatus){
                    mPlayer = MediaPlayer.create(this@PlayMusicInBGService, R.raw.sample)
                    mPlayer?.start()
                    playStatus=true
                }
            } catch (e: InterruptedException){
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun onCreate(){
        HandlerThread("ServiceStartArguments",Process.THREAD_PRIORITY_BACKGROUND).apply{
            start()

            serviceHandler = ServiceHandler(looper)
            serviceLooper = looper
        }
    }

    override fun onStartCommand(
        intent: Intent, flags: Int, startId: Int):Int{
        Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show()

        serviceHandler?.obtainMessage()?.also{ msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder?{
        return null
    }

    override fun onDestroy(){
        mPlayer?.stop()
        playStatus=false
        stopSelf()
        Toast.makeText(this,"service done",Toast.LENGTH_SHORT).show()

    }

}