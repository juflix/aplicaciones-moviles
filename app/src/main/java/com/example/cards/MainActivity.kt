package com.example.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkAnswerButton: Button = findViewById(R.id.check_answer_button)
        val separator_view: View = findViewById(R.id.separator_view)
        val answerTextView: TextView = findViewById(R.id.answer_text_view)
        val difficultyButtons: LinearLayout = findViewById(R.id.difficulty_buttons)

        checkAnswerButton.setOnClickListener {
            checkAnswerButton.visibility = View.INVISIBLE
            answerTextView.visibility = View.VISIBLE
            separator_view.visibility = View.VISIBLE
            difficultyButtons.visibility = View.VISIBLE
        }

        var card = Card("Tree", "Árbol")

        var easy_button: Button = findViewById(R.id.easy_button)
        var doubt_button: Button = findViewById(R.id.doubt_button)
        var difficult_button: Button = findViewById(R.id.difficult_button)

        easy_button.setOnClickListener {
            card.quality = 5
            card.update(LocalDateTime.now())
            Toast.makeText(applicationContext, String.format("%.1f", card.easiness), Toast.LENGTH_SHORT).show()
        }
        doubt_button.setOnClickListener {
            card.quality = 3
            card.update(LocalDateTime.now())
            Toast.makeText(applicationContext, String.format("%.1f", card.easiness), Toast.LENGTH_SHORT).show()
        }
        difficult_button.setOnClickListener {
            card.quality = 0
            card.update(LocalDateTime.now())
            Toast.makeText(applicationContext, String.format("%.1f", card.easiness), Toast.LENGTH_SHORT).show()
        }



    }
}
