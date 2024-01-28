package com.example.shift_tt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shift_tt.db.dao.Dao
import com.example.shift_tt.db.entity.EntityItem

@Database (entities = [EntityItem::class], version = 1)
abstract class MainDb : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "test.db"
            ).build()
        }
    }
}