package com.abplus.k2a2.model

interface BloodPressureRepository {
    fun add(bp: BloodPressure)
    fun save(bp: BloodPressure)
    fun delete(bp: BloodPressure)
    fun load(): List<BloodPressure>
    fun latest(): BloodPressure
}
