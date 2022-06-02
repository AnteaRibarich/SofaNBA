package com.example.sofanba

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sofanba.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val toolbar = binding.customToolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.appName.category.text =
            getString(R.string.app_name_category)
        binding.appName.value.text = getString(R.string.app_name)

        binding.credit.category.text = getString(R.string.credit)
        binding.credit.value.text = getString(R.string.balldontlie)

        binding.developer.category.text = getString(R.string.developer)
        binding.developer.value.text = getString(R.string.antea)
    }
}
