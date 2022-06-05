package com.example.sofanba.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofanba.databinding.FragmentPlayerStatisticsBinding
import com.example.sofanba.model.Average
import com.example.sofanba.model.Player
import com.example.sofanba.viewmodel.PlayerActivityViewModel

class PlayerStatisticsFragment(val player: Player) : Fragment() {
    private lateinit var viewModel: PlayerActivityViewModel
    private var _binding: FragmentPlayerStatisticsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[PlayerActivityViewModel::class.java].apply {}

        _binding = FragmentPlayerStatisticsBinding.inflate(inflater, container, false)
        val root = binding.root
        val textViews = listOf(
            binding.textSeason0,
            binding.textSeason1,
            binding.textSeason2,
            binding.textSeason2
        )
        val mappedValues = LinkedHashMap<String, MutableList<Any>>()

        binding.recyclerStats.layoutManager = LinearLayoutManager(requireContext())
        val adapter = context?.let {
            StatisticsAdapter(
                it,
                linkedMapOf()
            )
        }
        binding.recyclerStats.adapter = adapter

        viewModel.getSeasonAverages(player.id)
        viewModel.seasonAverages.observe(viewLifecycleOwner) {
            val seasonAverages = viewModel.seasonAverages.value as HashMap<Int, Average>

            for (i in seasonAverages.entries) {
                mappedValues.getOrPut("min", ::mutableListOf) += i.value.min
                mappedValues.getOrPut("fgm", ::mutableListOf) += i.value.fgm
                mappedValues.getOrPut("fga", ::mutableListOf) += i.value.fga
                mappedValues.getOrPut("fg3m", ::mutableListOf) += i.value.fg3m
                mappedValues.getOrPut("fg3a", ::mutableListOf) += i.value.fg3a
                mappedValues.getOrPut("ftm", ::mutableListOf) += i.value.ftm
                mappedValues.getOrPut("fta", ::mutableListOf) += i.value.fta
                mappedValues.getOrPut("oreb", ::mutableListOf) += i.value.oreb
                mappedValues.getOrPut("dreb", ::mutableListOf) += i.value.dreb
                mappedValues.getOrPut("reb", ::mutableListOf) += i.value.reb
                mappedValues.getOrPut("ast", ::mutableListOf) += i.value.ast
                mappedValues.getOrPut("blk", ::mutableListOf) += i.value.blk
                mappedValues.getOrPut("turnover", ::mutableListOf) += i.value.turnover
                mappedValues.getOrPut("pf", ::mutableListOf) += i.value.pf

                mappedValues.getOrPut("pts", ::mutableListOf) += i.value.pts
                mappedValues.getOrPut("fg_pct", ::mutableListOf) += i.value.fg_pct
                mappedValues.getOrPut("fg3_pct", ::mutableListOf) += i.value.fg3_pct
                mappedValues.getOrPut("ft_pct", ::mutableListOf) += i.value.ft_pct
            }
            adapter?.setStatistics(mappedValues)
            seasonAverages.keys.forEachIndexed { index, element ->
                textViews[index].text = element.toString()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
