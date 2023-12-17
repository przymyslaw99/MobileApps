package com.example.android_app.repository

import retrofit2.Response

class StarRepository {

    suspend fun getStarResponse(): Response<StarResponse> =
        StarService.starService.getStarResponse()
}