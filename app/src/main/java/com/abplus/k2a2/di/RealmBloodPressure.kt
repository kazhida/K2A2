package com.abplus.k2a2.di

import com.abplus.k2a2.model.BloodPressure
import com.abplus.k2a2.model.BloodPressureRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.Sort
import io.realm.kotlin.query.find
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmBloodPressure(
    @PrimaryKey
    var id: Long,
    var timeInMillis: Long,
    var systolicBP: Int,
    var diastolicBP: Int,
    var pulseRate: Int
): RealmObject {

    constructor() : this(0,0,0,0,0)

    fun toModel(): BloodPressure = BloodPressure(
        id,
        timeInMillis,
        systolicBP,
        diastolicBP,
        pulseRate
    )

    class Repository() : BloodPressureRepository {

        private val realm: Realm get() = RealmConfiguration.Builder(schema = setOf(RealmBloodPressure::class)).build().let {
            Realm.open(it)
        }

        override fun add(bp: BloodPressure) {
            realm.apply {
                writeBlocking {
                    val maxId = query(RealmBloodPressure::class).sort("id", Sort.DESCENDING).first().find()?.id ?: 0
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

        override fun save(bp: BloodPressure) {
            realm.apply {
                writeBlocking {
                    val record = query(RealmBloodPressure::class, "id = $0", bp.id).find().first()
                    record.apply {
                        timeInMillis = bp.timeInMillis
                        systolicBP = bp.systolicBP
                        diastolicBP = bp.diastolicBP
                        pulseRate = bp.pulseRate
                    }
                }
            }.close()
        }

        override fun delete(bp: BloodPressure) {
            realm.apply {
                writeBlocking {
                    query(RealmBloodPressure::class, "id = $0", bp.id).find().first().let {
                        delete(it)
                    }
                }
            }.close()
        }

        private val query: RealmQuery<RealmBloodPressure> get() =
            realm.query(RealmBloodPressure::class).sort("id", Sort.DESCENDING)

        override fun load(): List<BloodPressure> = query.find().map {
            it.toModel()
        }.also {
            realm.close()
        }

        override fun latest(): BloodPressure = query.first().find {
            it?.toModel()
        } ?: BloodPressure(
            0,
            System.currentTimeMillis(),
            130,
            80,
            60
        )
    }
}
