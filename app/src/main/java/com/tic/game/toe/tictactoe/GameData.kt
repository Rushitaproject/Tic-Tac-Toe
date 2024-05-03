package com.tic.game.toe.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object  GameData {

    private var gameModell : MutableLiveData<GameModel> = MutableLiveData()
    var gameModel : LiveData<GameModel> = gameModell


    fun saveGameModel(model: GameModel){
        gameModell.postValue(model)
    }
}