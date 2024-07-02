package com.example.bootcounter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.Minutes
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

// add unit tests
class GetNotificationBodyUseCase @Inject constructor(private val repo: BootCounterRepo) {

    /**
     * The DateTimeFormatter pattern YYYY returns the week based year, not the era-based year.
     * This means that 12/29/2019 will format to 2019, but 12/30/2019 will format to 2020!
     */
    private val dateFormatter = SimpleDateFormat(
        "DD/MM/yyyy HH:MM:SS", Locale.getDefault()
    )

    /**
     * suspend as storage call should be asynchronous
     */
    suspend fun getNotificationBody(): String = withContext(Dispatchers.IO) {
        val snapshot = repo.bootEventsSnapshot
        val count = snapshot.count()
        return@withContext if (count <= 0) {
            "No boots detected"
        } else if (count == 1) {
            val formattedDate = dateFormatter.format(snapshot.last().bootTime)
            "The boot was detected = $formattedDate"
        } else {
            val lastDate = snapshot.last()
            val preLastDate = snapshot[snapshot.size - 2]
            val delta = Minutes.minutesBetween(preLastDate.bootTime, lastDate.bootTime).minutes
            "Last boots time delta = $delta" // in minutes
        }
    }
}