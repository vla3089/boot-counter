package com.example.bootcounter

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.bootcounter.di.AppComponent
import com.example.bootcounter.di.DaggerAppComponent


class BootCounterApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .build()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    /* id = */ NOTIFICATIONS_CHANNEL_ID,
                    /* name = */ NOTIFICATIONS_CHANNEL,
                    /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
                )
            channel.description = NOTIFICATIONS_CHANNEL_DESCRIPTION

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}