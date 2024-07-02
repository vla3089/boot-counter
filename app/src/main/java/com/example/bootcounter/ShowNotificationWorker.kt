package com.example.bootcounter

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ShowNotificationWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker (
    context,
    workerParams
) {
    override suspend fun doWork(): Result {
        val useCase = (applicationContext as BootCounterApplication).appComponent.showBootNotificationUseCase
        useCase.showBootReceivedNotification(applicationContext)
        return Result.success()
    }
}
