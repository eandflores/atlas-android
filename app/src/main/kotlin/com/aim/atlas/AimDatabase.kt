package com.aim.atlas

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.aim.atlas.domain.User
import com.aim.atlas.domain.UserDao


@Database(entities = [(User::class)], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}