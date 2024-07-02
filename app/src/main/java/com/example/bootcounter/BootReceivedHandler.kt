package com.example.bootcounter

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class BootReceivedHandler(context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        showNotification(applicationContext);
        return Result.success()
    }

    private fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = buildNotification(context)

        // You can use a unique ID for each notification or use the same ID to update the same notification
        notificationManager.notify(1, notification)
    }

    private fun buildNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, "MY_CHANNEL_ID")
            .setContentTitle("My Notification")
            .setContentText("Hello from Worker!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

}
