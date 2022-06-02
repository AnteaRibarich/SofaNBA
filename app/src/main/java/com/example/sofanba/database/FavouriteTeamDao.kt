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

    @Query("DELETE FROM FavouriteTeam WHERE team_id = :id")
    suspend fun deleteFavouriteTeam(id: Int)

    @Query("SELECT * FROM FavouriteTeam ORDER BY orderNum_team DESC")
    suspend fun getAllFavouriteTeams(): List<Team>

    @Query("DELETE FROM FavouriteTeam")
    suspend fun deleteAllFavouriteTeams()

    @Query("UPDATE FavouriteTeam SET orderNum_team = :order WHERE team_id = :id")
    suspend fun updateOrder(id: Int, order: Int)
}
