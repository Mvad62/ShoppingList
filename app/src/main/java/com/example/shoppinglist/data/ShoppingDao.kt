package com.example.shoppinglist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping_items ORDER BY isBought ASC, id ASC")
    fun getAll(): Flow<List<ShoppingItem>>

    @Insert
    suspend fun insert(item: ShoppingItem)

    @Update
    suspend fun update(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("DELETE FROM shopping_items")
    suspend fun clearAll()
}