package com.example.sofanba.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.TerminalSeparatorType
import androidx.paging.insertHeaderItem
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentExploreBinding
import com.example.sofanba.network.paging.PlayerTeamDiff
import com.example.sofanba.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // setting up recycler view
        binding.recylyerViewExplore.layoutManager = LinearLayoutManager(requireContext())
        val pagingAdapter = PlayerPagingAdapter(requireContext(), PlayerTeamDiff, true)
        binding.recylyerViewExplore.adapter = pagingAdapter

        // setting up spinner
        val spinner = binding.spinner
        val adapter = context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.explore_options, R.layout.spinner_text
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (spinner.selectedItemId) {
                    1L -> {
                        pagingAdapter.setIsPlayer(false)
                        pagingAdapter.refresh()
                        lifecycleScope.launch {
                            viewModel.flowTeams.collectLatest {
                                pagingAdapter.submitData(
                                    it.map { x -> x as Any }
                                        .insertHeaderItem(
                                            TerminalSeparatorType.SOURCE_COMPLETE,
                                            item = spinner.selectedItem
                                        )
                                )
                            }
                        }
                    }
                    else -> {
                        pagingAdapter.setIsPlayer(true)
                        pagingAdapter.refresh()
                        lifecycleScope.launch {
                            viewModel.flowPlayers.collectLatest {
                                pagingAdapter.submitData(
                                    it.map { x -> x as Any }
                                        .insertHeaderItem(
                                            TerminalSeparatorType.SOURCE_COMPLETE,
                                            item = spinner.selectedItem
                                        )
                                )
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        viewModel.allFavouritePlayersData.observe(viewLifecycleOwner) {}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
