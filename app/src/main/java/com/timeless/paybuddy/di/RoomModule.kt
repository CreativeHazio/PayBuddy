package com.timeless.paybuddy.di

import android.content.Context
import androidx.room.Room
import com.timeless.paybuddy.data.local.database.UserDatabase
import com.timeless.paybuddy.data.local.user.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context : Context) : UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase) : UserDAO {
        return userDatabase.userDao
    }


}