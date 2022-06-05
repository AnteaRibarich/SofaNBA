package com.example.sofanba.model

data class ImageResponse(
    val data: List<Image>
)

data class Image(
    val playerId: Int,
    val imageUrl: String,
    val imageCaption: String,
    val id: Int
)
