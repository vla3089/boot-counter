package com.example.bootcounter.di

import com.example.bootcounter.BootCounterRepo
import com.example.bootcounter.MainActivity
import com.example.bootcounter.ShowBootNotificationUseCase
import com.example.bootcounter.ShowNotificationWorker
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    val repo: BootCounterRepo

    val showBootNotificationUseCase: ShowBootNotificationUseCase
}