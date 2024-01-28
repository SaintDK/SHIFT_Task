package com.example.shift_tt.Retrofit.api

import com.example.shift_tt.Retrofit.model.Person
import retrofit2.Call
import retrofit2.http.GET

interface RandomUserApi {
    @GET("api")
    fun getRandomUser(): Call<Person>
}
