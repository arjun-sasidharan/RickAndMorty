package com.example.rickandmorty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.characterByIdLiveData.observe(this) { response ->

            // updating data to epoxy controller
            epoxyController.characterResponse = response

            if (response == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Unsuccessful network call",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }
        }
        viewModel.refreshCharacter(54)

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}