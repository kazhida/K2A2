package com.abplus.k2a2.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class BloodPressure(
    val id: Long,
    val timeInMillis: Long,
    val systolicBP: Int,
    val diastolicBP: Int,
    val pulseRate: Int = 0
) : Parcelable {

    companion object {

        fun create(
            dateTime: Long,
            systolic: Int,
            diastolic: Int,
            pulse: Int = 0
        ): BloodPressure = BloodPressure(0, dateTime, systolic, diastolic, pulse)
    }

    @IgnoredOnParcel
    private val dateFormatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    @IgnoredOnParcel
    private val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    @IgnoredOnParcel
    private val calendar = Calendar.getInstance().also {
        it.timeInMillis = timeInMillis
    }

    val date: String get() = dateFormatter.format(calendar.time)
    val time: String get() = timeFormatter.format(calendar.time)
}
