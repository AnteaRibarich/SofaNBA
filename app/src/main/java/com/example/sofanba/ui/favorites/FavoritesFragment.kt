package com.example.sofanba.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentFavoritesBinding
import com.example.sofanba.model.DataWrapperHelper
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team
import com.example.sofanba.viewmodel.MainActivityViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // setup adapter
        binding.recylyerViewFavourites.layoutManager = LinearLayoutManager(requireContext())
        val adapter = context?.let { FavouritesAdapter(it, listOf(), listOf(), false, DataWrapperHelper(it, viewModel)) }
        binding.recylyerViewFavourites.adapter = adapter

        // setup editing
        binding.imageEditing.setOnClickListener {
            it.isSelected = !it.isSelected
            adapter?.setEditing(it.isSelected)
        }

        // observe list of favourite teams and players
        viewModel.allFavouritePlayersData.observe(viewLifecycleOwner) {
            if (viewModel.allFavouritePlayersData.value != null && adapter != null)
            {
                adapter.setFavouritePlayers(viewModel.allFavouritePlayersData.value as List<Player>)
            }
        }

        viewModel.allFavouriteTeamsData.observe(viewLifecycleOwner) {
            if (viewModel.allFavouriteTeamsData.value != null && adapter != null)
            {
                adapter.setFavouriteTeams(viewModel.allFavouriteTeamsData.value as List<Team>)
            }
        }

        context?.let { viewModel.getAllFavouriteTeams(it) }
        context?.let { viewModel.getAllFavouritePlayers(it) }

        binding.toolbar.textToolbarTitle.text = getString(R.string.title_favorites)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
