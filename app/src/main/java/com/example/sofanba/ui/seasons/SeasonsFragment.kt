package com.example.sofanba.ui.seasons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentSeasonsBinding

class SeasonsFragment : Fragment() {

    private var _binding: FragmentSeasonsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val seasonsViewModel =
            ViewModelProvider(this).get(SeasonsViewModel::class.java)

        _binding = FragmentSeasonsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSeasons
        seasonsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.toolbar.textToolbarTitle.text = getString(R.string.title_seasons)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
