package com.example.rickandmorty.characters.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.NavGraphDirections
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = CharacterDetailsEpoxyController { episodeClickedId ->
            val navDirections = NavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(
                episodeClickedId
            )
            findNavController().navigate(navDirections)
        }
        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) { character ->

            // updating data to epoxy controller
            epoxyController.character = character

            if (character == null) {
                Toast.makeText(
                    requireActivity(),
                    "Unsuccessful network call",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }
        }

        viewModel.fetchCharacter(characterId = safeArgs.charactedId)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
