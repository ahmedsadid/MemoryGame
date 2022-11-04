package edu.qc.seclass.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.qc.seclass.memorygame.models.BoardSize
import edu.qc.seclass.memorygame.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView


    private var boardSize: BoardSize = BoardSize.MEDIUM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard= findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()

        //adapter provides binding for data set to the views of RV
        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, randomizedImages)
        rvBoard.setHasFixedSize(true)
        //measures and positions item views
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())

    }
}