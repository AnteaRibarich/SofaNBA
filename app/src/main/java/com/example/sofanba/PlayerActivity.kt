package com.example.sofanba

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.sofanba.databinding.ActivityPlayerBinding
import com.example.sofanba.model.Player
import com.example.sofanba.ui.explore.EXTRA_PLAYER
import com.example.sofanba.ui.player.SectionsPagerAdapter
import com.example.sofanba.viewmodel.PlayerActivityViewModel
import com.google.android.material.tabs.TabLayout

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.getSerializableExtra(EXTRA_PLAYER) as Player

        binding.title.text = player.getFullName()
        binding.imageArrow.setOnClickListener {
            super.finish()
        }

        viewModel.getAllFavouritePlayers(this)
        viewModel.allFavouritePlayersData.observe(this) {
            if (viewModel.allFavouritePlayersData.value?.contains(player) == true) {
                binding.imageViewFavourite.isSelected = true
            }
        }

        binding.imageViewFavourite.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                viewModel.insertFavouritePlayer(this, player)
            } else {
                viewModel.deleteFavouritePlayer(this, player.id)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, player)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}
