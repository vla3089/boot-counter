package com.example.bootcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager


class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val workRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(BootReceivedHandler::class.java).build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}
