package com.example.bootcounter

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.joda.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

// todo: migrate to room, store msFromEpoc
@Singleton
class BootCounterRepo @Inject constructor() {
    private val _bootEventsFlow = MutableStateFlow(listOf<BootEvent>())

    val bootEvents: Flow<List<BootEvent>>
        get() = _bootEventsFlow

    val bootEventsSnapshot: List<BootEvent>
        get() = _bootEventsFlow.value

    fun addBootEvent(bootEvent: BootEvent) {
        val snapshot = bootEventsSnapshot.toMutableList()
        snapshot.add(bootEvent)
        _bootEventsFlow.value = snapshot
    }
}

data class BootEvent(val bootTime: LocalDateTime)
