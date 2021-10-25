package com.example.pingplus
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel()
{
    var gameList = ArrayList<GameItem>()

    fun createGame(gamename:String,creatorname: String,addy:String,time:String,pMax:String) : GameItem{
        return GameItem(gamename,creatorname,addy,time,0,pMax)
    }
    fun addGame(g : GameItem){ gameList.add(g) }
}
