package com.example.sofanba.ui.favorites

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofanba.PlayerActivity
import com.example.sofanba.R
import com.example.sofanba.TeamActivity
import com.example.sofanba.model.DataWrapperHelper
import com.example.sofanba.model.Image
import com.example.sofanba.model.NBAhelper
import com.example.sofanba.model.Player
import com.example.sofanba.model.Team
import com.example.sofanba.model.TeamHelper
import com.example.sofanba.ui.explore.PlayerPagingAdapter

const val STRING_TYPE = 1
const val PLAYER_TYPE = 2
const val TEAM_TYPE = 3
const val EXTRA_TEAM = "extra_team"
const val EXTRA_PLAYER = "extra_player"

class FavouritesAdapter(
    private val context: Context,
    private var playerFavList: List<Player>,
    private var teamFavList: List<Team>,
    private var editing: Boolean,
    private val dataWrapper: DataWrapperHelper,
    private var images: HashMap<Int, Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterList = arrayListOf<Any>()

    init {
        updateList()
    }

    fun setImages(images: HashMap<Int, Any>) {
        this.images = images
        notifyDataSetChanged()
    }

    private fun updateList() {
        adapterList.clear()
        adapterList.add(context.getString(R.string.players))
        adapterList.addAll(playerFavList)
        adapterList.add(context.getString(R.string.teams))
        adapterList.addAll(teamFavList)
        notifyDataSetChanged()
        println(editing)
    }

    override fun getItemViewType(position: Int): Int {
        // position to type
        return if (position == 0 || position == playerFavList.size + 1) {
            STRING_TYPE
        } else if (position > 0 && position < playerFavList.size + 1) {
            PLAYER_TYPE
        } else {
            TEAM_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // view type to viewholder
        return when (viewType) {
            STRING_TYPE -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.text_view_subtitle, parent, false)
                PlayerPagingAdapter.StringViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.player_list_tile, parent, false)
                PlayerPagingAdapter.PlayerViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            STRING_TYPE -> {
                // set text
                val stringHolder = holder as PlayerPagingAdapter.StringViewHolder
                if (adapterList.elementAt(position) is CharSequence) {
                    stringHolder.binding.root.text = adapterList.elementAt(position) as CharSequence
                }
            }
            PLAYER_TYPE -> {
                // fill player data
                val playerHolder = holder as PlayerPagingAdapter.PlayerViewHolder
                if (adapterList.elementAt(position) is Player) {
                    val player = adapterList.elementAt(position) as Player
                    playerHolder.binding.textPlayerName.text = player.getFullName()
                    playerHolder.binding.textTeam.text = player.team.abbreviation
                    playerHolder.binding.imageViewFavourite.isSelected = true

                    // favourite actions
                    playerHolder.binding.imageViewFavourite.setOnClickListener {
                        it.isSelected = !it.isSelected
                        if (it.isSelected) {
                            dataWrapper.insertFavouritePlayer(player)
                        } else {
                            dataWrapper.deleteFavouritePlayer(player)
                        }
                    }

                    // change appereance according to editing flag
                    if (editing) {
                        playerHolder.binding.layoutInner.layoutParams.width =
                            NBAhelper.dpsToPixels(288, context)
                    } else {
                        playerHolder.binding.layoutInner.layoutParams.width =
                            NBAhelper.dpsToPixels(344, context)
                    }

                    playerHolder.binding.root.setOnClickListener {
                        val intent = Intent(context, PlayerActivity::class.java).apply {
                            putExtra(EXTRA_PLAYER, player)
                        }
                        context.startActivity(intent)
                    }

                    if (images[player.id] is Int || !images.containsKey(player.id)) {
                        playerHolder.binding.imagePlayer.setImageDrawable(
                            AppCompatResources.getDrawable(
                                context,
                                when (player.id % 3) {
                                    0 -> R.drawable.ic_assets_exportable_graphics_player_1_big
                                    1 -> R.drawable.ic_assets_exportable_graphics_player_2_big
                                    else -> R.drawable.ic_assets_exportable_graphics_player_3_big
                                }
                            )
                        )
                    } else {
                        val image = images[player.id] as List<Image>
                        playerHolder.binding.imagePlayer.load(image[0].imageUrl)
                    }
                }
            }
            TEAM_TYPE -> {
                // fill team data
                val teamHolder = holder as PlayerPagingAdapter.PlayerViewHolder
                if (adapterList.elementAt(position) is Team) {
                    val team = adapterList.elementAt(position) as Team
                    val teamHelper =
                        TeamHelper.values().firstOrNull { it.name == team.abbreviation }
                    teamHolder.binding.textPlayerName.text = team.full_name
                    teamHolder.binding.textTeam.visibility = View.GONE
                    teamHolder.binding.imageViewFavourite.isSelected = true
                    teamHolder.binding.imagePlayer.setImageIcon(null)
                    if (teamHelper != null) {
                        teamHolder.binding.imageTeam.setImageDrawable(
                            AppCompatResources.getDrawable(
                                context,
                                teamHelper.src
                            )
                        )
                        teamHolder.binding.imagePlayer.backgroundTintList =
                            context.getColorStateList(teamHelper.color)
                    }

                    // favourite actions
                    teamHolder.binding.imageViewFavourite.setOnClickListener {
                        it.isSelected = !it.isSelected
                        if (it.isSelected) {
                            dataWrapper.insertFavouriteTeam(team)
                        } else {
                            dataWrapper.deleteFavouriteTeam(team)
                        }
                    }

                    // change appereance according to editing flag
                    if (editing) {
                        teamHolder.binding.layoutInner.layoutParams.width =
                            NBAhelper.dpsToPixels(288, context)
                    } else {
                        teamHolder.binding.layoutInner.layoutParams.width =
                            NBAhelper.dpsToPixels(344, context)
                    }

                    teamHolder.binding.root.setOnClickListener {
                        val intent = Intent(context, TeamActivity::class.java).apply {
                            putExtra(EXTRA_TEAM, team)
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return playerFavList.size + teamFavList.size + 2
    }

    // setters which cal update list on change
    fun setFavouritePlayers(list: List<Player>) {
        this.playerFavList = list
        updateList()
    }

    fun setFavouriteTeams(list: List<Team>) {
        this.teamFavList = list
        updateList()
    }

    fun setEditing(editing: Boolean) {
        this.editing = editing
        updateList()
    }
}
