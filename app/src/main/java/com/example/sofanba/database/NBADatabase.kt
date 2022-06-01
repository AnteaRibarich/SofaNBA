package com.example.sofanba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team

@Database(entities = [Player::class, Team::class], version = 1, exportSchema = false)
abstract class NBADatabase : RoomDatabase() {
    abstract fun favouritePlayerDao(): FavouritePlayerDao
    abstract fun favouriteTeamDao(): FavouriteTeamDao

    companion object {
        private var instance: NBADatabase? = null

        fun getDatabase(context: Context): NBADatabase? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                NBADatabase::class.java,
                "NBADatabase"
            ).build()
    }
}
