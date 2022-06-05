package com.example.sofanba.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class GameResponse(
    val data: List<Game>,
    val meta: Meta
)

data class Game(
    val id: Int,
    val date: String,
    val home_team: Team,
    val home_team_score: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitor_team: Team,
    val visitor_team_score: Int
) : Serializable {
    fun getMonthYear(): String = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(parseDate())

    fun getDayDate(): String = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.ENGLISH).format(parseDate())

    fun parseDate(): Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(date)
        ?: Calendar.getInstance().time

    fun getDay(): String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(parseDate())

    fun getDateNoYear(): String = SimpleDateFormat("dd MMM", Locale.ENGLISH).format(parseDate())

    fun getTeamNames(): String = "${home_team.name} vs. ${visitor_team.name}"
}
