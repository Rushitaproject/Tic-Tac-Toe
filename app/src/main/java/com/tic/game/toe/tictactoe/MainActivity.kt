package com.tic.game.toe.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tic.game.toe.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var  binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay?.setOnClickListener(View.OnClickListener {
            createGame()
        })

    }


    fun createGame(){
        GameData.saveGameModel(GameModel(gameStatus = GameStatus.JOINED))
        startGame()
    }

    fun startGame(){
       startActivity(Intent(this,GameActivity ::class.java))
    }

}