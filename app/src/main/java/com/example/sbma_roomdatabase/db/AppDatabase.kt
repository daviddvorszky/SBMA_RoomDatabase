package com.example.sbma_roomdatabase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Player::class),(Team::class)], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao

    companion object{
        private var sInstance: AppDatabase? = null
        @Synchronized
        fun get(context: Context): AppDatabase{
            if(sInstance == null){
                sInstance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db").build()
            }
            return sInstance!!
        }
    }


}