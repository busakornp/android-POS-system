package com.stamford.pos

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.setting.view.*

class CommandCenter : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    private val TAG = "CommandCenterActivity"
    private val CHANNEL_ID = "90200"
    private val notificationId = 1
    private val EXTRA_NOTIFICATION_ID = "notif_id"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.commandcenter)

//        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//
//        val username = preferences.getString("USERNAME", "")
//        usernameEditTextTextPersonName2.setText(username)
//        val password = preferences.getString("PASSWORD", "")
//        passwordEditTextTextPassword.setText(password)


        val newbutton = findViewById<Button>(R.id.button_new_order)
        newbutton.setOnClickListener {
            val myIntent = Intent(this, SecondActivity::class.java)
            startActivity(myIntent)

        }
        val addbutton = findViewById<Button>(R.id.button_add_product)
        addbutton.setOnClickListener {
            val myIntent = Intent(this, ProductCRUDActivity::class.java)
            startActivity(myIntent)

        }
    }
    fun onclick_stopbtn(view: View) {
        Log.i(TAG, "Stop music in bg clicked")
        Intent(this@CommandCenter,
            PlayMusicInBGService::class.java).also { intent -> stopService(intent) }

    }
    fun onclick_playbtn(view: View) {
        Log.i(TAG, "Play music in bg clicked")
        Intent(this@CommandCenter, PlayMusicInBGService::class.java).also { intent ->
            startService(intent)
        }


        val intent = Intent(this, CommandCenter::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        val playIntent = Intent(this, PlayMusicBroadcastReceiver::class.java).apply {
            action = ACTION_POS_PLAY
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }
        val playPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, playIntent, 0)


        val stopIntent = Intent(this, PlayMusicBroadcastReceiver::class.java).apply {
            action = ACTION_POS_STOP
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)

        }

        val stopPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, stopIntent, 0)

        createNotificationChannel()

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bg_music_stat)
            .setContentTitle("Stamford POS BG Music Status")
            .setContentText("Status Playing")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .addAction(R.drawable.ic_action_play, getString(R.string.play_str), playPendingIntent)
            .addAction(R.drawable.ic_action_stop, getString(R.string.stop_str), stopPendingIntent)
            with(NotificationManagerCompat.from(this)) {

                    notify(notificationId, builder.build())
                }


        }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Play BG Status Channel"
            val descriptionText = "A notification to show play background music status"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply{
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}


