package com.aim.atlas.db

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.aim.atlas.vo.User


@Database(entities = [(User::class)], version = 1)
abstract class AtlasDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}