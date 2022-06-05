package com.example.sofanba.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentPlayerDetailsBinding
import com.example.sofanba.model.Image
import com.example.sofanba.model.Player
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.viewmodel.PlayerActivityViewModel
import kotlinx.coroutines.launch

class PlayerDetailsFragment(val player: Player) : Fragment() {
    private lateinit var viewModel: PlayerActivityViewModel
    private var _binding: FragmentPlayerDetailsBinding? = null

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

        _binding = FragmentPlayerDetailsBinding.inflate(inflater, container, false)
        val root = binding.root

        val teamHelper =
            TeamHelper.values().firstOrNull { it.name == player.team.abbreviation }

        binding.textTeamName.text = player.team.full_name

        // position
        binding.textPosition.text = context?.let { player.getPlayerPosition(it) }
        // weight
        binding.textWeightValue.text = player.getPlayerWeight()
        // height
        binding.textHeightValue.text = player.getPlayerHeight()

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

        lifecycleScope.launch {
            viewModel.playerImage.observe(viewLifecycleOwner) {
                val image = viewModel.playerImage.value

                if (image !is Image) {
                    binding.imagePlayer.setImageDrawable(
                        context?.let { it1 ->
                            AppCompatResources.getDrawable(
                                it1,
                                when (player.id % 3) {
                                    0 -> R.drawable.ic_assets_exportable_graphics_player_1_big
                                    1 -> R.drawable.ic_assets_exportable_graphics_player_2_big
                                    else -> R.drawable.ic_assets_exportable_graphics_player_3_big
                                }
                            )
                        }
                    )
                } else {
                    binding.imagePlayer.load(image.imageUrl)
                }
            }
        }
        viewModel.getPlayerImages(player.id)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
