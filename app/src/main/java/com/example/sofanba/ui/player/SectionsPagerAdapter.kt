package com.example.sofanba.ui.player

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sofanba.R
import com.example.sofanba.model.Player

private val TAB_TITLES = arrayOf(
    R.string.details,
    R.string.statistics,
    R.string.matches
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, val player: Player) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PlayerDetailsFragment(player)
            1 -> PlayerStatisticsFragment(player)
            else -> PlayerMatchesFragment(player)
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}
