package com.example.sofanba

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sofanba.databinding.ActivityTeamBinding
import com.example.sofanba.model.Team
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.ui.explore.EXTRA_TEAM
import com.example.sofanba.ui.team.SectionsPagerAdapter
import com.example.sofanba.viewmodel.TeamActivityViewModel
import com.google.android.material.tabs.TabLayout

class TeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamBinding
    private val viewModel: TeamActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val team = intent.getSerializableExtra(EXTRA_TEAM) as Team
        val teamHelper =
            TeamHelper.values().firstOrNull { it.name == team.abbreviation }

        // color tabs, app_bar
        if (teamHelper != null) {
            binding.tabs.backgroundTintList = applicationContext.getColorStateList(teamHelper.color)
            binding.appBar.backgroundTintList = applicationContext.getColorStateList(teamHelper.color)
        }
        binding.title.text = team.full_name
        binding.imageArrow.setOnClickListener {
            super.finish()
        }

        viewModel.getAllFavouriteTeams(this)
        viewModel.allFavouriteTeamsData.observe(this) {
            if (viewModel.allFavouriteTeamsData.value?.contains(team) == true) {
                binding.imageViewFavourite.isSelected = true
            }
        }

        binding.imageViewFavourite.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                viewModel.insertFavouriteTeam(this, team)
            } else {
                viewModel.deleteFavouriteTeam(this, team.id)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, team)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}
