package com.example.sofanba.model

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
    @PrimaryKey
    val id: Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val height_feet: Int?,
    val height_inches: Int?,
    val weight_pounds: Int?,
    @Embedded
    val team: Team,
    val orderNum: Int?
)

@Entity(tableName = "FavouriteTeam")
data class Team(
    @PrimaryKey
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)

data class Meta(
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
)
