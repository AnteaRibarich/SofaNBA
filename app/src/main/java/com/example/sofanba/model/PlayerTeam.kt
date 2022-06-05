package com.example.sofanba.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sofanba.R
import java.io.Serializable

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
) : Serializable {
    fun getPlayerWeight(): String {
        return if (weight_pounds == null) {
            "..."
        } else {
            "${weight_pounds}lb"
        }
    }

    fun getPlayerPosition(context: Context): String {
        return when (position) {
            "F" -> context.getString(R.string.forward)
            "G" -> context.getString(R.string.guard)
            else -> context.getString(R.string.center)
        }
    }

    fun getPlayerHeight(): String {
        return if (height_feet == null || height_inches == null) {
            "..."
        } else "$height_feet’$height_inches”"
    }

    fun getFullName(): String = "$first_name $last_name"
}

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
) : Serializable

data class Meta(
    val total_pages: Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
)
