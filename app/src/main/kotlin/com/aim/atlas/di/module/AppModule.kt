package com.aim.atlas.di.module

import android.app.Application
import dagger.Provides
import javax.inject.Singleton
import com.aim.atlas.db.UserDao
import android.arch.persistence.room.Room
import com.aim.atlas.api.AtlasService
import com.aim.atlas.db.AtlasDB
import com.aim.atlas.di.ViewModelModule
import com.aim.atlas.util.LiveDataCallAdapterFactory
import dagger.Module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [(ViewModelModule::class)])
internal class AppModule {
    @Singleton
    @Provides
    fun provideAtlasService(): AtlasService {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create<AtlasService>(AtlasService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AtlasDB {
        return Room.databaseBuilder(app, AtlasDB::class.java, "atlas.db").build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AtlasDB): UserDao {
        return db.userDao()
    }
}