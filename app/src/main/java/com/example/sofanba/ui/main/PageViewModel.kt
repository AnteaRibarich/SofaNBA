package com.example.sofanba.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sofanba.model.Player

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val player = MutableLiveData<Player>()

    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}
