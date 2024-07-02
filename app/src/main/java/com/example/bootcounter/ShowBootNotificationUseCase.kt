package com.example.bootcounter

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import javax.inject.Inject

// todo: convert to class and inject
class ShowBootNotificationUseCase @Inject constructor(
    private val bodyUseCase: GetNotificationBodyUseCase
) {
    suspend fun showBootReceivedNotification(context: Context) {
        showNotification(context, bodyUseCase.getNotificationBody())
    }

    private fun showNotification(context: Context, body: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = buildNotification(context, body)

        notificationManager.notify(BOOT_RECEIVED_NOTIFICATION_ID, notification)
    }

    private fun buildNotification(context: Context, body: String): Notification {
        return NotificationCompat.Builder(context, "MY_CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("My Notification")
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}
