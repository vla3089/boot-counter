package com.example.bootcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.joda.time.LocalDateTime

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {

            (context as BootCounterApplication).appComponent.repo.addBootEvent(
                BootEvent(LocalDateTime.now())
            )
        }
    }
}