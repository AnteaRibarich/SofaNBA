package com.example.sofanba.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sofanba.model.Team

@Dao
interface FavouriteTeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteTeam(team: Team)

    @Query("DELETE FROM FavouriteTeam WHERE id = :id")
    suspend fun deleteFavouriteTeam(id: Int)

    @Query("SELECT * FROM FavouriteTeam ORDER BY orderdNum DESC")
    suspend fun getAllFavouriteTeams(): List<Team>

    @Query("DELETE FROM FavouriteTeams")
    suspend fun deleteMyCities()

    @Query("UPDATE FavouriteTeams SET orderdNum = :order WHERE id = :id")
    suspend fun updateOrder(id: Int, order: Int)
}
