package com.example.sofanba.ui.team

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sofanba.R
import com.example.sofanba.TeamActivity
import com.example.sofanba.databinding.TeamTileLayoutBinding
import com.example.sofanba.model.Team
import com.example.sofanba.model.TeamHelper

const val EXTRA_TEAM = "extra_team"

class TeamPagingAdapter(
    private val context: Context,
    differCallback: DiffUtil.ItemCallback<Any>
) : PagingDataAdapter<Any, TeamPagingAdapter.TeamViewHolder>(differCallback) {

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TeamTileLayoutBinding.bind(itemView)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        // fill team data
        if (getItem(position) is Team) {
            val team = getItem(position) as Team
            val teamHelper =
                TeamHelper.values().firstOrNull { it.name == team.abbreviation }

            if (teamHelper != null) {
                holder.binding.imageTeam.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        teamHelper.src
                    )
                )
                holder.binding.viewBackgroundTeam.backgroundTintList =
                    context.getColorStateList(teamHelper.color)
            }
            holder.binding.textTeam.text = team.abbreviation

            holder.binding.root.setOnClickListener {
                val intent = Intent(context, TeamActivity::class.java).apply {
                    putExtra(EXTRA_TEAM, team)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.team_tile_layout, parent, false)
        return TeamViewHolder(view)
    }
}
