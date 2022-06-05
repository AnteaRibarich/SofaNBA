package com.example.sofanba.network

import com.example.sofanba.model.GameResponse
import com.example.sofanba.model.PlayerResponse
import com.example.sofanba.model.SeasonAverage
import com.example.sofanba.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BallDontLieService {

    @GET("players")
    suspend fun getAllPlayers(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<PlayerResponse>

    @GET("teams")
    suspend fun getAllTeams(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<TeamResponse>

    @GET("games")
    suspend fun getAllGames(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<GameResponse>

    @GET("games")
    suspend fun getAllGamesFromTeam(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("team_ids[]") id: Int
    ): Response<GameResponse>

    @GET("season_averages")
    suspend fun getAllSeasonAverages(
        @Query("season") season: Int,
        @Query("player_ids[]") playerId: Int
    ): Response<SeasonAverage>
}
