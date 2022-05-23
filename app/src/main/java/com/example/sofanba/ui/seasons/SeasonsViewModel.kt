package com.example.sofanba.ui.seasons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeasonsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is seasons Fragment"
    }
    val text: LiveData<String> = _text
}
