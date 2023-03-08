package com.imams.simpleform.data.di.module

import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.data.repository.RegistrationDataRepositoryImpl
import com.imams.simpleform.data.source.local.RegistrationDao
import com.imams.simpleform.data.source.local.RegistrationDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RegistrationDataModule {

    @Provides
    @Singleton
    fun provideRegistrationRepo(registrationDao: RegistrationDao): RegistrationDataRepository {
        return RegistrationDataRepositoryImpl(registrationDao)
    }

    @Provides
    @Singleton
    fun provideRegistrationDao(database: RegistrationDb): RegistrationDao {
        return database.registrationDao()
    }

}