package ru.sample.duckapp

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import ru.sample.duckapp.data.DucksApi
import ru.sample.duckapp.infra.Api


class MainActivity : AppCompatActivity(){
    private val ducksApi: DucksApi = Api.getInstance().create(DucksApi::class.java)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val editText = findViewById<EditText>(R.id.editTextNumber)

        button.setOnClickListener {
            val code: String = editText.text.toString()
            when {
                isValidHttpCode(code) -> fetchHttpDuckImage(code)
                code.isEmpty() -> fetchRandomDuckImage()
                else -> Toast.makeText(this, "Invalid HTTP code", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // Function to validate the HTTP code
    fun isValidHttpCode(code: String): Boolean {
        val validCodes = setOf(
            "100", "200", "301", "302", "400", "403", "404", "409", "413", "418", "420", "426", "429", "451", "500"
        )
        return validCodes.contains(code)
    }

    // Function to fetch a duck image for a specific HTTP code
    fun fetchHttpDuckImage(code: String) {
        GlobalScope.launch {

            val response = ducksApi.getRandomDuck()
            Log.d("RetrofitResponse", response.body().toString())

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        val url = "https://random-d.uk/api/http/$code"
                        Picasso.get().load(url).into(findViewById<ImageView>(R.id.imageView))
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    // Function to fetch a random duck image
    fun fetchRandomDuckImage() {
        // launching a new coroutine for unblocking request
        GlobalScope.launch {

            val response = ducksApi.getRandomDuck()
            Log.d("RetrofitResponse", response.body().toString())

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        val url = items.url
                        Picasso.get().load(url).into(findViewById<ImageView>(R.id.imageView))
                    }
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

}