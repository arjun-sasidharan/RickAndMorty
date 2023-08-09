package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        rickAndMortyService.getCharacterById().enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i("MainActivity", "onResponse: ${response.toString()}")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i("MainActivity", "onFailure: ${t.message ?: "Null Message"}")
            }

        })
    }
}