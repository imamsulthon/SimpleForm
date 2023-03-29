package com.imams.simpleform.data.di.module

import android.content.Context
import com.imams.simpleform.data.repository.ProvinceDataRepository
import com.imams.simpleform.data.repository.ProvinceDataRepositoryImpl
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.data.repository.RegistrationDataRepositoryImpl
import com.imams.simpleform.data.source.local.dao.RegistrationDao
import com.imams.simpleform.data.source.local.RegistrationDb
import com.imams.simpleform.data.source.local.dao.ProvinceDao
import com.imams.simpleform.data.source.remote.ProvinceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideProvinceRepo(provinceApi: ProvinceApi, provinceDao: ProvinceDao, @ApplicationContext context: Context): ProvinceDataRepository {
        return ProvinceDataRepositoryImpl(provinceApi, provinceDao, context)
    }

    @Provides
    @Singleton
    fun provideRegistrationDao(database: RegistrationDb): RegistrationDao {
        return database.registrationDao()
    }

    @Provides
    @Singleton
    fun provideProvinceDao(database: RegistrationDb): ProvinceDao {
        return database.provinceDao()
    }

}