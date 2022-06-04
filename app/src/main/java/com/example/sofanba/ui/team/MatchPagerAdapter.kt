package com.example.sofanba.ui.team

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sofanba.R
import com.example.sofanba.databinding.TeamMatchListItemBinding
import com.example.sofanba.model.Game
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.model.Team
import com.google.android.material.color.MaterialColors

class MatchPagerAdapter(
    private val context: Context,
    differCallback: DiffUtil.ItemCallback<Game>,
    private val team: Team
) : PagingDataAdapter<Game, MatchPagerAdapter.MatchViewHolder>(differCallback) {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TeamMatchListItemBinding.bind(itemView)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        // fill team data
        if (getItem(position) is Game) {
            val game = getItem(position) as Game

            holder.binding.textMonth.text = game.getMonthYear()
            holder.binding.textMonth.visibility = View.VISIBLE
            if (position > 0) {
                // hide if previous equal
                val previousGame = getItem(position - 1)
                if (game.getMonthYear() == previousGame?.getMonthYear()) {
                    holder.binding.textMonth.visibility = View.GONE
                }
            }

            holder.binding.textDay.text = game.getDay()
            holder.binding.textDate.text = game.getDateNoYear()

            val isVisitor = game.visitor_team.abbreviation == team.abbreviation
            val teamHelper: TeamHelper?
            if (isVisitor) {
                holder.binding.textVsAt.text = context.getString(R.string.at)
                teamHelper =
                    TeamHelper.values().firstOrNull { it.name == game.home_team.abbreviation }
                holder.binding.textResultLeft.text = game.visitor_team_score.toString()
                holder.binding.textResultRight.text = game.home_team_score.toString()
                if (game.visitor_team_score > game.home_team_score) {
                    holder.binding.textWinLose.text = "W"
                    holder.binding.textWinLose.setTextColor(
                        MaterialColors.getColor(
                            context,
                            R.attr.colorSuccess,
                            Color.BLACK
                        )
                    )
                } else {
                    holder.binding.textWinLose.text = "L"
                    holder.binding.textWinLose.setTextColor(
                        MaterialColors.getColor(
                            context,
                            R.attr.colorError,
                            Color.BLACK
                        )
                    )
                }
            } else {
                holder.binding.textVsAt.text = context.getString(R.string.vs)
                teamHelper =
                    TeamHelper.values().firstOrNull { it.name == game.visitor_team.abbreviation }
                holder.binding.textResultRight.text = game.visitor_team_score.toString()
                holder.binding.textResultLeft.text = game.home_team_score.toString()
                if (game.home_team_score > game.visitor_team_score) {
                    holder.binding.textWinLose.text = "W"
                    holder.binding.textWinLose.setTextColor(
                        MaterialColors.getColor(
                            context,
                            R.attr.colorSuccess,
                            Color.BLACK
                        )
                    )
                } else {
                    holder.binding.textWinLose.text = "L"
                    holder.binding.textWinLose.setTextColor(
                        MaterialColors.getColor(
                            context,
                            R.attr.colorError,
                            Color.BLACK
                        )
                    )
                }
            }

            if (teamHelper != null) {
                holder.binding.imageTeamVs.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        teamHelper.src
                    )
                )
                holder.binding.textTeamVsName.text = teamHelper.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.team_match_list_item, parent, false)
        return MatchViewHolder(view)
    }
}
