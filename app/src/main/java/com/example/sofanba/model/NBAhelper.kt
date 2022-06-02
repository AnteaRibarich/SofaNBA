package com.example.sofanba.model

import android.content.Context

class NBAhelper {
    companion object {
        fun dpsToPixels(dps: Int, context: Context): Int {
            val scale: Float = context.resources?.displayMetrics?.density ?: 0F
            return (dps * scale + 0.5f).toInt()
        }
    }
}
