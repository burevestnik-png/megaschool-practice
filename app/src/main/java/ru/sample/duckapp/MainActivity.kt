package ru.sample.duckapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import ru.sample.duckapp.infra.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.sample.duckapp.data.DucksApi

class MainActivity : AppCompatActivity(){
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.imageView)

        button.setOnClickListener {
            val ducksApi = Api.getInstance().create(DucksApi::class.java)
            // launching a new coroutine for unblocking request
            GlobalScope.launch {

                val response = ducksApi.getRandomDuck()
                Log.d("RetrofitResponse", response.body().toString())

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val items = response.body()
                        if (items != null) {
                            val url = items.url
                            Picasso.get().load(url).into(imageView)
                        }
                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                    }
                }
            }
        }


    }

}