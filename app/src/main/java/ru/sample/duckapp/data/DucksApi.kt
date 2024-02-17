package ru.sample.duckapp.data

import retrofit2.Response
import retrofit2.http.GET
import ru.sample.duckapp.domain.Duck

interface DucksApi {
    @GET("random")
    suspend fun getRandomDuck(): Response<Duck>
}