package com.example.sofanba.ui.player

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sofanba.R
import com.example.sofanba.databinding.PlayerStatsLayoutBinding
import com.google.android.material.color.MaterialColors


class StatisticsAdapter(
    private val context: Context,
    private var statistics: LinkedHashMap<String, MutableList<Any>>
) : RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder>() {

    class StatisticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = PlayerStatsLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        // view type to viewholder
        val view = LayoutInflater.from(context).inflate(R.layout.player_stats_layout, parent, false)
        return StatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        // fill player data
        val stats = statistics.toList()[position]
        holder.binding.textCategory.text = stats.first

        val textViews = listOf(
            holder.binding.textSeason0,
            holder.binding.textSeason1,
            holder.binding.textSeason2,
            holder.binding.textSeason2
        )

        stats.second.forEachIndexed { index, element ->
            textViews[index].text = element.toString()
        }

        val indexOfMax = stats.second.indices.maxByOrNull { stats.second[it] as Int } ?: -1
        textViews[indexOfMax].setTextColor(
            MaterialColors.getColor(
                context,
                R.attr.colorPrimaryDefault,
                Color.BLACK
            )
        )
        textViews[indexOfMax].backgroundTintList = context.getColorStateList(R.color.color_primary_hightlight)
        textViews[indexOfMax].typeface = Typeface.DEFAULT_BOLD
    }

    override fun getItemCount(): Int {
        return statistics.size
    }

    // setters which cal update list on change
    fun setStatistics(statistics: LinkedHashMap<String, MutableList<Any>>) {
        this.statistics = statistics
        notifyDataSetChanged()
    }

}
