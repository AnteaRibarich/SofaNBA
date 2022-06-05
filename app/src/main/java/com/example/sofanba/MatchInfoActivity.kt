package com.example.sofanba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sofanba.databinding.ActivityMatchInfoBinding
import com.example.sofanba.model.Game
import com.example.sofanba.ui.matchInfo.SectionsPagerAdapter
import com.example.sofanba.ui.seasons.EXTRA_GAME
import com.google.android.material.tabs.TabLayout

class MatchInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game = intent.getSerializableExtra(EXTRA_GAME) as Game

        binding.titleTeams.text = game.getTeamNames()
        binding.imageArrow.setOnClickListener {
            super.finish()
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, game)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}
