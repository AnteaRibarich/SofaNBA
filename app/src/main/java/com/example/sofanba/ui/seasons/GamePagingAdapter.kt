package com.example.sofanba.ui.seasons

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sofanba.MatchInfoActivity
import com.example.sofanba.R
import com.example.sofanba.databinding.MatchTileBinding
import com.example.sofanba.model.Game
import com.example.sofanba.model.TeamHelper
import com.google.android.material.color.MaterialColors

const val EXTRA_GAME = "game"

class GamePagingAdapter(
    private val context: Context,
    differCallback: DiffUtil.ItemCallback<Game>
) : PagingDataAdapter<Game, GamePagingAdapter.GameViewHolder>(differCallback) {

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        // fill player data

        val game = getItem(position)
        if (game != null) {
            // home team
            val teamHelperHome =
                TeamHelper.values().firstOrNull { it.name == game.home_team.abbreviation }
            if (teamHelperHome != null) {
                holder.binding.viewBackgroundTeamLeft.backgroundTintList =
                    context.getColorStateList(teamHelperHome.color)

                holder.binding.imageTeamLeft.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        teamHelperHome.src
                    )
                )
            }
            holder.binding.textTeamLeft.text = game.home_team.abbreviation
            holder.binding.textResultLeft.text = game.home_team_score.toString()

            // team away
            val teamHelperAway =
                TeamHelper.values().firstOrNull { it.name == game.visitor_team.abbreviation }
            if (teamHelperAway != null) {
                holder.binding.viewBackgroundTeamRight.backgroundTintList =
                    context.getColorStateList(teamHelperAway.color)

                holder.binding.imageTeamRight.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        teamHelperAway.src
                    )
                )
            }
            holder.binding.textTeamRight.text = game.visitor_team.abbreviation
            holder.binding.textResultRight.text = game.visitor_team_score.toString()

            holder.binding.textStatus.text = game.status

            // result color
            if (game.home_team_score > game.visitor_team_score) {
                holder.binding.textResultRight.setTextColor(
                    MaterialColors.getColor(
                        context,
                        R.attr.colorNLv2,
                        Color.BLACK
                    )
                )
                holder.binding.textResultLeft.setTextColor(
                    MaterialColors.getColor(
                        context,
                        R.attr.colorNLv1,
                        Color.BLACK
                    )
                )
            } else {
                holder.binding.textResultLeft.setTextColor(
                    MaterialColors.getColor(
                        context,
                        R.attr.colorNLv2,
                        Color.BLACK
                    )
                )
                holder.binding.textResultRight.setTextColor(
                    MaterialColors.getColor(
                        context,
                        R.attr.colorNLv1,
                        Color.BLACK
                    )
                )
            }
            // date
            holder.binding.textDate.text = game.getDayDate()

            // month and month visibility
            holder.binding.textMonth.text = game.getMonthYear()
            holder.binding.textMonth.visibility = View.VISIBLE
            if (position > 0) {
                // hide if previous equal
                val previousGame = getItem(position - 1)
                if (game.getMonthYear() == previousGame?.getMonthYear()) {
                    holder.binding.textMonth.visibility = View.GONE
                }
            }

            holder.binding.root.setOnClickListener {
                val intent = Intent(context, MatchInfoActivity::class.java).apply {
                    putExtra(EXTRA_GAME, game)
                }
                context.startActivity(intent)
            }
        }
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MatchTileBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.match_tile, parent, false)
        return GameViewHolder(view)
    }
}
