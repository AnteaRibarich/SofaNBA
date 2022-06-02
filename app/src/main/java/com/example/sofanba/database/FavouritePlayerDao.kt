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

    @Query("DELETE FROM FavouritePlayer WHERE player_id = :id")
    suspend fun deleteFavouritePlayer(id: Int)

    @Query("SELECT * FROM FavouritePlayer ORDER BY orderNum_player DESC")
    suspend fun getAllFavouritePlayers(): List<Player>

    @Query("DELETE FROM FavouritePlayer")
    suspend fun deleteAllFavouritePlayers()

    @Query("UPDATE FavouritePlayer SET orderNum_player = :order WHERE player_id = :id")
    suspend fun updateOrder(id: Int, order: Int)
}
