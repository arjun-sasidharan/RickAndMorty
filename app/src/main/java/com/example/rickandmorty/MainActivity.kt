package com.example.rickandmorty

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.characterByIdLiveData.observe(this) { character ->

            // updating data to epoxy controller
            epoxyController.character = character

            if (character == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Unsuccessful network call",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }
        }
        viewModel.refreshCharacter(intent.getIntExtra(Constants.INTENT_EXTRA_CHARACTER_ID, 1))

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}