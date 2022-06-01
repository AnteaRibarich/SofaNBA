package com.example.sofanba.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sofanba.model.Player

@Dao
interface FavouritePlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritePlayer(player: Player)

    @Query("DELETE FROM FavouritePlayer WHERE id = :id")
    suspend fun deleteFavouritePlayer(id: Int)

    @Query("SELECT * FROM FavouritePlayer ORDER BY orderdNum DESC")
    suspend fun getAllFavouritePlayers(): List<Player>

    @Query("DELETE FROM FavouritePlayer")
    suspend fun deleteMyCities()

    @Query("UPDATE FavouritePlayer SET orderdNum = :order WHERE id = :id")
    suspend fun updateOrder(id: Int, order: Int)
}
