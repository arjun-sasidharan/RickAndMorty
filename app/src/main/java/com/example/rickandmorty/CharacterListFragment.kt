package com.example.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmorty.characters.CharacterListPagingEpoxyController
import com.example.rickandmorty.characters.CharactersViewModel

class CharacterListFragment : Fragment() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // connecting page list live data with epoxy controller to show in the UI
        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) {pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
        findNavController().navigate(R.id.action_characterListFragment_to_characterDetailFragment)
    }
}