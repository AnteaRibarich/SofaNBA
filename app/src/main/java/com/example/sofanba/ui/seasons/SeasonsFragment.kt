package com.example.sofanba.ui.seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentSeasonsBinding
import com.example.sofanba.network.paging.GameDiff
import com.example.sofanba.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeasonsFragment : Fragment() {

    private var _binding: FragmentSeasonsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSeasonsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // setting up recycler view
        binding.recylyerViewSeasons.layoutManager = LinearLayoutManager(requireContext())
        val pagingAdapter = context?.let {
            GamePagingAdapter(
                requireContext(), GameDiff
            )
        }
        binding.recylyerViewSeasons.adapter = pagingAdapter

        lifecycleScope.launch {
            viewModel.flowGames.collectLatest {
                pagingAdapter?.submitData(it)
            }
        }

        binding.toolbar.textToolbarTitle.text = getString(R.string.title_seasons)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
