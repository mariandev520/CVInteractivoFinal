package com.example.myapplication

import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getUser(): User
}
