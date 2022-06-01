package com.example.sofanba.network.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team

object PlayerTeamDiff : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is Player && newItem is Player) {
            oldItem.id == newItem.id
        } else if (oldItem is Team && newItem is Team) {
            oldItem.id == newItem.id
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is Player && newItem is Player) {
            oldItem == newItem
        } else if (oldItem is Team && newItem is Team) {
            oldItem == newItem
        } else {
            false
        }
    }
}
