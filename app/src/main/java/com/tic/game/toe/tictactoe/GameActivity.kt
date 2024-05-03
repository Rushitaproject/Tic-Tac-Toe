package com.tic.game.toe.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tic.game.toe.tictactoe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityGameBinding

    private var gameModel: GameModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //changes

        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btnStart.setOnClickListener(View.OnClickListener {
            startGame()
        })
        GameData.gameModel.observe(this) {
            gameModel = it
            setUI()
        }

    }

    fun setUI() {
        gameModel?.apply {
            binding.btn0.text = filledPostion[0]
            binding.btn1.text = filledPostion[1]
            binding.btn2.text = filledPostion[2]
            binding.btn3.text = filledPostion[3]
            binding.btn4.text = filledPostion[4]
            binding.btn5.text = filledPostion[5]
            binding.btn6.text = filledPostion[6]
            binding.btn7.text = filledPostion[7]
            binding.btn8.text = filledPostion[8]


            binding.btnStart.visibility = View.VISIBLE

            binding.txtGameNt.text =
                when (gameStatus) {
                    GameStatus.CREATED -> {
                        binding.btnStart.visibility = View.INVISIBLE
                        "Game ID :" + gameId
                    }

                    GameStatus.JOINED -> {
                        "Click On Start Game"
                    }

                    GameStatus.INPROGRESS -> {
                        binding.btnStart.visibility = View.INVISIBLE
                        currentPlayer + "turn"
                    }

                    GameStatus.FINISHED -> {
                        if (winner.isNotEmpty()) winner + "Won"
                        else "DRAW"
                    }
                }
        }
    }

    fun checkForWinner(){
        val winningPos = arrayOf(
            intArrayOf(0,1,2),
            intArrayOf(3,4,5),
            intArrayOf(6,7,8),
            intArrayOf(0,3,6),
            intArrayOf(1,4,7),
            intArrayOf(2,5,8),
            intArrayOf(0,4,8),
            intArrayOf(2,4,6),
        )

        gameModel?.apply {
            for (i in winningPos) {
                if (
                    filledPostion[i[0]] == filledPostion[i[1]] &&
                    filledPostion[i[1]] == filledPostion[i[2]] &&
                    filledPostion[i[0]].isNotEmpty()
                ) {
                    gameStatus = GameStatus.FINISHED
                    winner = filledPostion[i[0]]
                }

               if( filledPostion.none(){it.isEmpty()}){
                   gameStatus = GameStatus.FINISHED
               }

                updateGameData(this)
            }
        }
    }

    fun startGame() {
        gameModel?.apply {
            updateGameData(GameModel(
                gameId = gameId,
                gameStatus = GameStatus.INPROGRESS
            ))
        }
    }

    fun updateGameData(model: GameModel) {
        GameData.saveGameModel(model)
    }

    override fun onClick(p0: View?) {
         gameModel?.apply {
             if(gameStatus!= GameStatus.INPROGRESS){
                 Toast.makeText(applicationContext,"Game not started",Toast.LENGTH_LONG).show()
                 return
             }
             val clickedPos =(p0?.tag as String).toInt()
             if(filledPostion[clickedPos].isEmpty()){
                 filledPostion[clickedPos] = currentPlayer
                 currentPlayer = if(currentPlayer =="X") "O" else "X"
                 checkForWinner()
                 updateGameData(this)

             }
         }
    }
}


