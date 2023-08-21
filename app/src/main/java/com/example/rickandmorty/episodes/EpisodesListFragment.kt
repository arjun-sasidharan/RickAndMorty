package com.example.rickandmorty.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickandmorty.NavGraphDirections
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodesListFragment : Fragment(R.layout.fragment_episode_list) {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)

        val epoxyController = EpisodeListEpoxyController { episodeClickedId ->
            val navDirections = NavGraphDirections.actionGlobalToEpisodeDetailBottomSheetFragment(
                episodeClickedId
            )
            findNavController().navigate(navDirections)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<EpisodeUiModel> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}