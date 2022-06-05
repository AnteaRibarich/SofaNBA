package com.example.sofanba.network

import com.example.sofanba.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SofaService {

    @GET("player-image/player/{playerId}")
    suspend fun getAllPlayerImages(
        @Path("playerId") playerId: Int
    ): Response<ImageResponse>
}
