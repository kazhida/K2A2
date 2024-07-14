package com.abplus.k2a2.di

import com.abplus.k2a2.model.BloodPressureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object RealmBloodPressureModule {

    @Provides
    fun provideBloodPressureRepository(): BloodPressureRepository = RealmBloodPressure.Repository()
}