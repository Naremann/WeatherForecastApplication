package com.example.weatherforecastapplication.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return Room.databaseBuilder(appContext, MyDatabase::class.java, MyDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLocationDao(myDatabase: MyDatabase): LocationDao {
        return myDatabase.getLocationDao()
    }

    @Provides
    fun provideWeatherAlertDao(myDatabase: MyDatabase):WeatherAlertDao{
        return myDatabase.getWeatherAlertDao()
    }
}


