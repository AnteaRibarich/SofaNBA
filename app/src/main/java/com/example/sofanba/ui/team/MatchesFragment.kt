package com.example.sofanba.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofanba.databinding.FragmentMatchesBinding
import com.example.sofanba.model.Team
import com.example.sofanba.network.paging.GameDiff
import com.example.sofanba.viewmodel.TeamActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MatchesFragment(val team: Team) : Fragment() {

    private lateinit var viewModel: TeamActivityViewModel
    private var _binding: FragmentMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TeamActivityViewModel::class.java].apply {}

        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.recylyerViewMatches.layoutManager = LinearLayoutManager(requireContext())
        val pagingAdapter = context?.let {
            MatchPagerAdapter(
                requireContext(),
                GameDiff,
                team
            )
        }
        binding.recylyerViewMatches.adapter = pagingAdapter

        viewModel.flowSet.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.flowGames.collectLatest {
                    pagingAdapter?.submitData(it)
                }
            }
        }

        viewModel.getFlowGamesFromTeam(team)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
