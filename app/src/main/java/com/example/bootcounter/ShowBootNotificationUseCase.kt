package com.example.bootcounter

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

// todo: convert to class and inject
object ShowBootNotificationUseCase {

    fun showBootReceivedNotification(context: Context, bootsInterceptedCount: Int) {
        showNotification(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = buildNotification(context)

        notificationManager.notify(BOOT_RECEIVED_NOTIFICATION_ID, notification)
    }

    private fun buildNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, "MY_CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("My Notification")
            .setContentText("Hello from Worker!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}
