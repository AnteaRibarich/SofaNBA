package com.example.sofanba.model

data class SeasonAverage(
    val data: List<Average>
)

data class Average(
    val games_played: Int,
    val player_id: Int,
    val season: Int,
    val min: String,
    val fgm: Float,
    val fga: Float,
    val fg3m: Float,
    val fg3a: Float,
    val ftm: Float,
    val fta: Float,
    val oreb: Float,
    val dreb: Float,
    val reb: Float,
    val ast: Float,
    val stl: Float,
    val blk: Float,
    val turnover: Float,
    val pf: Float,
    val pts: Float,
    val fg_pct: Float,
    val fg3_pct: Float,
    val ft_pct: Float
)
