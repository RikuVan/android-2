package fi.monad.asteroidradar.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toFormattedDatePlus(days: Int = 0): String {
    val dateFormat = SimpleDateFormat(this, Locale.getDefault())
    return try {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)
        dateFormat.format(calendar.time)
    } catch (e: ParseException) {
        throw(e)
    }
}