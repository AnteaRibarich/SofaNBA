package com.example.sofanba.ui.matchInfo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sofanba.R
import com.example.sofanba.databinding.FragmentMatchDetailsBinding
import com.example.sofanba.model.Game
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.viewmodel.TeamActivityViewModel
import com.google.android.material.color.MaterialColors

/**
 * A placeholder fragment containing a simple view.
 */
class MatchDetailsFragment(val game: Game) : Fragment() {

    private lateinit var viewModel: TeamActivityViewModel
    private var _binding: FragmentMatchDetailsBinding? = null

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

        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.matchTile.viewBackground.elevation = 0F
        binding.matchTile.textMonth.visibility = View.GONE

        val teamHelperHome =
            TeamHelper.values().firstOrNull { it.name == game.home_team.abbreviation }
        val teamHelperAway =
            TeamHelper.values().firstOrNull { it.name == game.visitor_team.abbreviation }

        if (teamHelperHome != null && context != null) {
            binding.matchTile.viewBackgroundTeamLeft.backgroundTintList =
                requireContext().getColorStateList(teamHelperHome.color)

            binding.matchTile.imageTeamLeft.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    teamHelperHome.src
                )
            )
        }
        binding.matchTile.textTeamLeft.text = game.home_team.abbreviation
        binding.matchTile.textResultLeft.text = game.home_team_score.toString()

        // team away
        if (teamHelperAway != null) {
            binding.matchTile.viewBackgroundTeamRight.backgroundTintList =
                context?.getColorStateList(teamHelperAway.color)

            binding.matchTile.imageTeamRight.setImageDrawable(
                context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        teamHelperAway.src
                    )
                }
            )
        }
        binding.matchTile.textTeamRight.text = game.visitor_team.abbreviation
        binding.matchTile.textResultRight.text = game.visitor_team_score.toString()

        binding.matchTile.textStatus.text = game.status

        // result color
        if (game.home_team_score > game.visitor_team_score) {
            binding.matchTile.textResultRight.setTextColor(
                MaterialColors.getColor(
                    requireContext(),
                    R.attr.colorNLv2,
                    Color.BLACK
                )
            )
            binding.matchTile.textResultLeft.setTextColor(
                MaterialColors.getColor(
                    requireContext(),
                    R.attr.colorNLv1,
                    Color.BLACK
                )
            )
        } else if (context != null) {
            binding.matchTile.textResultLeft.setTextColor(
                MaterialColors.getColor(
                    requireContext(),
                    R.attr.colorNLv2,
                    Color.BLACK
                )
            )
            binding.matchTile.textResultRight.setTextColor(
                MaterialColors.getColor(
                    requireContext(),
                    R.attr.colorNLv1,
                    Color.BLACK
                )
            )
        }
        // date
        binding.matchTile.textDate.text = game.getDayDate()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
