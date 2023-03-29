package com.imams.simpleform.data.di.module

import android.app.Application
import androidx.room.Room
import com.imams.simpleform.data.source.local.RegistrationDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseFactory {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): RegistrationDb {
        return Room.databaseBuilder(
            app,
            RegistrationDb::class.java,
            "form_registration_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

}