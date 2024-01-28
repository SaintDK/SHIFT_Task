package com.example.shift_tt.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shift_tt.db.ItemDetails
import com.example.shift_tt.db.entity.EntityItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    fun insertItem(item: EntityItem)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<EntityItem>>

    @Query("DELETE FROM items")
    fun deleteAll()

    @Query("SELECT id, phoneNumber FROM items")
    fun getAllItemNames(): Flow<List<ItemDetails>>

    @Query("SELECT * FROM items WHERE id = :userId")
    fun getUserById(userId: String): LiveData<EntityItem> // Предполагается, что id типа String

}