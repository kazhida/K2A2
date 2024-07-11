package com.abplus.k2a2.di

import com.abplus.k2a2.model.BloodPressure
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmBloodPressure(
    @PrimaryKey
    var id: Long = 0,
    var timeInMillis: Long = 0,
    var systolicBP: Int = 0,
    var diastolicBP: Int = 0,
    var pulseRate: Int = 0
): RealmObject {

    fun toModel(): BloodPressure = BloodPressure(id, timeInMillis, systolicBP, diastolicBP, pulseRate)

    class Repository() : BloodPressure.Repository {

        private val realm: Realm get() = RealmConfiguration.Builder(schema = setOf(RealmBloodPressure::class)).build().let {
            Realm.open(it)
        }

        override suspend fun add(bp: BloodPressure) {
            realm.apply {
                write {
                    val maxId = query(RealmBloodPressure::class).max("id", RealmBloodPressure::class).find()?.id ?: 0
                    RealmBloodPressure(
                        id = maxId + 1,
                        timeInMillis = bp.timeInMillis,
                        systolicBP = bp.systolicBP,
                        diastolicBP = bp.diastolicBP,
                        pulseRate = bp.pulseRate
                    ).let {
                        copyToRealm(it)
                    }
                }
            }.close()
        }

        override suspend fun save(bp: BloodPressure) {
            realm.apply {
                write {
                    RealmBloodPressure(
                        id = bp.id,
                        timeInMillis = bp.timeInMillis,
                        systolicBP = bp.systolicBP,
                        diastolicBP = bp.diastolicBP,
                        pulseRate = bp.pulseRate
                    ).let {
                        copyToRealm(it)
                    }
                }
            }.close()
        }

        override suspend fun delete(bp: BloodPressure) {
            TODO("Not yet implemented")
        }

        override suspend fun load(): List<BloodPressure> = realm.let { realm ->
            realm.query(RealmBloodPressure::class).sort("timeInMillis", Sort.DESCENDING).find().map {
                it.toModel()
            }.also {
                realm.close()
            }
        }
    }
}
