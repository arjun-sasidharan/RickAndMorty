package com.example.rickandmorty.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.databinding.FragmentCharacterListBinding

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()

    private val epoxyController = CharacterListPagingEpoxyController(::onCharacterSelected)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // connecting page list live data with epoxy controller to show in the UI
        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
        val directions =
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId
            )
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}