package com.abplus.k2a2.model

import java.text.SimpleDateFormat
import java.util.*

data class BloodPressure(
    val id: Long,
    val timeInMillis: Long,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int = 0
) {

    companion object {

        fun newInstance(
            dateTime: Long,
            systolic: Int,
            diastolic: Int,
            pulse: Int = 0
        ): BloodPressure = BloodPressure(0, dateTime, systolic, diastolic, pulse)
    }

    private val dateFormatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    private val calendar = Calendar.getInstance().also {
        it.timeInMillis = timeInMillis
    }

    val date: String get() = dateFormatter.format(calendar.time)
    val time: String get() = timeFormatter.format(calendar.time)

    interface Repository {
        fun add(bp: BloodPressure)
        fun save(bp: BloodPressure)
        fun delete(bp: BloodPressure)
        fun load(): List<BloodPressure>
    }
}
