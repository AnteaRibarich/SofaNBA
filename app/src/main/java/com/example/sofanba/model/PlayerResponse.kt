package com.example.sofanba.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class PlayerResponse(
    val data: List<Player>,
    val meta: Meta
)

data class TeamResponse(
    val data: List<Team>,
    val meta: Meta
)

@Entity(tableName = "FavouritePlayer")
data class Player(
    @ColumnInfo("player_id")
    @PrimaryKey
    val id: Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val height_feet: Int?,
    val height_inches: Int?,
    val weight_pounds: Int?,
    val orderNum_player: Int = 0,
    @Embedded
    val team: Team
)

@Entity(tableName = "FavouriteTeam")
data class Team(
    @ColumnInfo("team_id")
    @PrimaryKey
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String,
    val orderNum_team: Int = 0
)

data class Meta(
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
)
