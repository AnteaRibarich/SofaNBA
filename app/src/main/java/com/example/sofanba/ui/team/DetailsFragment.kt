package com.example.sofanba.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofanba.databinding.FragmentTeamBinding
import com.example.sofanba.model.Team
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.network.paging.PlayerTeamDiff
import com.example.sofanba.viewmodel.TeamActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment(val team: Team) : Fragment() {

    private lateinit var viewModel: TeamActivityViewModel
    private var _binding: FragmentTeamBinding? = null

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

        val teamHelper =
            TeamHelper.values().firstOrNull { it.name == team.abbreviation }

        viewModel.team.observe(viewLifecycleOwner) {
            println(viewModel.team.value)
        }

        lifecycleScope.launch {
            viewModel.flowTeams.collectLatest {
                println(it)
            }
        }

        _binding = FragmentTeamBinding.inflate(inflater, container, false)
        val root = binding.root
        binding.textTeamAbbreviation.text = team.abbreviation
        binding.textTeamName.text = team.full_name
        binding.textCity.text = team.city
        binding.textConfValue.text = team.conference
        binding.textDivValue.text = team.division
        if (teamHelper != null) {
            binding.imageTeam.setImageDrawable(
                context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        teamHelper.src
                    )
                }
            )
            binding.viewBackgroundTeam.backgroundTintList =
                context?.getColorStateList(teamHelper.color)
        }

        binding.recylyerViewOtherTeams.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val pagingAdapter = context?.let {
            TeamPagingAdapter(
                requireContext(), PlayerTeamDiff
            )
        }
        binding.recylyerViewOtherTeams.adapter = pagingAdapter

        lifecycleScope.launch {
            viewModel.flowTeams.collectLatest {
                pagingAdapter?.submitData(
                    it.filter { x -> x.conference == team.conference }
                        .map { x -> x as Any }
                )
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
