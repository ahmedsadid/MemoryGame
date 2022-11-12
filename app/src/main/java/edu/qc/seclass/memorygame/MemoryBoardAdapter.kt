package edu.qc.seclass.memorygame

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.qc.seclass.memorygame.models.BoardSize
import kotlin.math.min

class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cardImages: List<Int>
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    private var clickedImage = cardImages[0];
    private var clickCount = 0;
    private var firstImage = -1;
    private var secondImage = -1;
    private var firstPos = -1;
    private var secondPos = -1;
    private var score = 0;
    private var matchedPairs = mutableListOf<Int>()

    companion object{
        private const val MARGIN_SIZE = 10
        private const val TAG= "MemoryBoardAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardWidth = parent.width / boardSize.getWidth() - (2 * MARGIN_SIZE)
        val cardHeight = parent.height / boardSize.getHeight() - (2 * MARGIN_SIZE)
        val cardSideLength = min(cardWidth, cardHeight)

        val view  =  LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)
        //val mainView = LayoutInflater.from(context).inflate(R.layout.activity_main, parent)
        val layoutParams = view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = cardSideLength
        layoutParams.height = cardSideLength
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)

    }

    override fun getItemCount() = boardSize.numCards

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        private val blackout = itemView.findViewById<ImageView>(R.id.blackout)
        //val scoreDisplay: TextView = parentView.findViewById(R.id.tvNumPairs) as TextView

        fun bind(position: Int) {
            imageButton.setImageResource(cardImages[position])
            imageButton.setOnClickListener{
                clickCount++
                clickedImage = cardImages[position]
                blackout.visibility = ImageView.INVISIBLE
                Log.i(TAG, matchedPairs.toString())

                if (clickCount % 2 != 0) {
                    firstImage = clickedImage
                    firstPos = position
                    Log.i(TAG,"" + clickCount)
                    Log.i(TAG, "Initial Choice (pos: " + position + ", id: " + clickedImage + ")")
                } else {
                    Log.i(TAG,"" + clickCount)
                    secondImage = clickedImage
                    secondPos = position
                    Log.i(TAG, "Second Choice (pos: " + position + ", id: " + clickedImage + ")")
                    if (firstImage == secondImage) {
                        if (position != firstPos) {
                            if (!matchedPairs.contains(firstPos) && !matchedPairs.contains(secondPos)) {
                                score++
                                Log.i(TAG, "MATCHED")
                                Log.i(TAG, "SCORE: " + score)
                                matchedPairs.add(firstPos)
                                matchedPairs.add(secondPos)
                            } else {
                                Log.i(TAG, "REPEATED PAIR")
                                Log.i(TAG, "SCORE: " + score)
                                //blackout.visibility = ImageView.VISIBLE
                            }
                        }
                    } else {
                        Log.i(TAG, "NOT MATCHED")
                        Log.i(TAG, "SCORE: " + score)
                        //blackout.visibility = ImageView.VISIBLE
                    }

                }

                //scoreDisplay.text = "Pairs: $score/4"
            }
        }

    }
}
