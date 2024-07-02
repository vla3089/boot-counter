package com.example.bootcounter

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

interface NotificationsPermissionsUseCase {

    fun shouldShowRequestPermissionRationale(activity: Activity): Boolean

    fun areNotificationsEnabled(): Boolean

    fun isNotificationPermissionGranted(activity: Activity): Boolean

    companion object {
        fun build(activity: AppCompatActivity): NotificationsPermissionsUseCase =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                NotificationsPermissionsUseCaseImpl(activity)
            } else {
                NotificationsPermissionsUseCaseCompat(activity)
            }
    }
}

// implementation for Android versions prior targetSdk 33
private class NotificationsPermissionsUseCaseCompat(activity: Activity) :
    NotificationsPermissionsUseCase {

    val notificationManager =
        activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun shouldShowRequestPermissionRationale(activity: Activity): Boolean = false

    override fun areNotificationsEnabled(): Boolean = notificationManager.areNotificationsEnabled()

    override fun isNotificationPermissionGranted(activity: Activity): Boolean = true
}

// implementation for Android versions targetSdk 33+
// activity scope
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class NotificationsPermissionsUseCaseImpl(activity: AppCompatActivity) :
    NotificationsPermissionsUseCase {

    val notificationManager =
        activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun shouldShowRequestPermissionRationale(activity: Activity): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        )

    override fun areNotificationsEnabled(): Boolean = notificationManager.areNotificationsEnabled()

    override fun isNotificationPermissionGranted(activity: Activity): Boolean =
        ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
}
