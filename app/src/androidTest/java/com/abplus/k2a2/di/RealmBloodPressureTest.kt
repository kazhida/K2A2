package com.abplus.k2a2.di

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abplus.k2a2.model.BloodPressure
import dagger.hilt.android.testing.HiltAndroidTest
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RealmBloodPressureTest {

    private val seed: List<RealmBloodPressure> = (0..4).map {
        RealmBloodPressure(
            it + 1L,
            System.currentTimeMillis() - 1000 * 60 * 60 * 24 * (it - 10),
            160 + it,
            100 + it,
            60 + it
        )
    }

    @Before
    fun init() {
        RealmConfiguration.Builder(schema = setOf(RealmBloodPressure::class)).build().let {
            Realm.open(it).apply {
                writeBlocking {
                    deleteAll()
                    seed.forEach {
                        copyToRealm(it)
                    }
                }
            }.close()
        }
    }

    @After
    fun clean() {
        RealmConfiguration.Builder(schema = setOf(RealmBloodPressure::class)).build().let {
            Realm.open(it).apply {
                writeBlocking {
                    deleteAll()
                }
            }.close()
        }
    }

    private val repository get() = RealmBloodPressureModule.provideBloodPressureRepository()

    @Test
    fun `5個のデータを読み込んで降順に並んでいることを確認`() {
        val records = repository.load()
        Assert.assertEquals(5, records.size)
        val sorted = records.sortedByDescending { it.id }
        Assert.assertEquals(sorted, records)
    }

    @Test
    fun `1個のデータを追加したらデータが6個になっている`() {
        BloodPressure.create(System.currentTimeMillis(), 100, 80, 60).let {
            repository.add(it)
        }
        val records = repository.load()
        Assert.assertEquals(6, records.size)
    }


    @Test
    fun `最新のデータを読み込む`() {
        val record = repository.latest()
        Assert.assertEquals(164, record.systolicBP)
        Assert.assertEquals(104, record.diastolicBP)
        Assert.assertEquals(64, record.pulseRate)
    }

    @Test
    fun `最新のデータを変更する`() {
        val source = repository.latest()
        source.copy(systolicBP = 169, diastolicBP = 109, pulseRate = 69).let {
            repository.save(it)
        }

        val record = repository.latest()
        Assert.assertEquals(169, record.systolicBP)
        Assert.assertEquals(109, record.diastolicBP)
        Assert.assertEquals(69, record.pulseRate)
    }

    @Test
    fun `3番目のデータを削除したらデータが4つになる`() {
        val records = repository.load()
        repository.delete(records[2])
        Assert.assertEquals(4, repository.load().size)
    }
}
