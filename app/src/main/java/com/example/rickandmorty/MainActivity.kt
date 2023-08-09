package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val headerImageView = findViewById<ImageView>(R.id.headerImageView)
        val genderImageView = findViewById<ImageView>(R.id.genderImageView)
        val aliveTextView = findViewById<TextView>(R.id.aliveTextView)
        val originTextView = findViewById<TextView>(R.id.originTextView)
        val speciesTextView = findViewById<TextView>(R.id.speciesTextView)

        // Moshi is for serialization and deserialization of json, retrofit handle making the network call
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService: RickAndMortyService =
            retrofit.create(RickAndMortyService::class.java)

        // since return type of getCharacterById method is call, we can do enqueue on the method
        // this is basically asynchronous execution
        rickAndMortyService.getCharacterById(10)
            .enqueue(object : Callback<GetCharacterByIdResponse> {
                override fun onResponse(
                    call: Call<GetCharacterByIdResponse>,
                    response: Response<GetCharacterByIdResponse>
                ) {
                    Log.i("MainActivity", "onResponse: ${response.toString()}")

                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity,
                            "Unsuccessful network call",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }
                    val body = response.body()

                    body.let { body ->
                        nameTextView.text = body?.name
                        aliveTextView.text = body?.status
                        speciesTextView.text = body?.species
                        originTextView.text = body?.origin?.name
                        body?.image.let { imageUrl ->
                            Picasso.get().load(imageUrl).into(headerImageView);
                        }
                        body?.gender.let { gender ->
                            if (gender!!.equals("male", ignoreCase = true)) {
                                genderImageView.setImageResource(R.drawable.ic_male_24)
                            } else {
                                genderImageView.setImageResource(R.drawable.ic_female_24)
                            }

                        }
                    }
                }

                override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                    Log.i("MainActivity", "onFailure: ${t.message ?: "Null Message"}")
                }

            })
    }
}