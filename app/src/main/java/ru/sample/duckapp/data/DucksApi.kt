package ru.sample.duckapp.data

import android.widget.ImageView
import retrofit2.Call
import retrofit2.http.GET
import ru.sample.duckapp.domain.Duck
import kotlin.random.Random


interface DucksApi {
    @GET("random")
    fun getRandomDuck(): Call<Duck>
}