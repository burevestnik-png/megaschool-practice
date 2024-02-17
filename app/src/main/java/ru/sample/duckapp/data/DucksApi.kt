package ru.sample.duckapp.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.sample.duckapp.domain.Duck

interface DucksApi {
    @GET("random")
    suspend fun getRandomDuck(): Response<Duck>

    @GET("/http/{code}")
    fun getHttpDuck(@Path("code") code: String): Call<ResponseBody>
}