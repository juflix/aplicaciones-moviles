package com.example.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.cards.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var card = Card("Tree", "Árbol")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.card = card

        binding.apply {
            answerButton.setOnClickListener {
                card?.answered = true
                invalidateAll()
            }
            easyButton.setOnClickListener {
                card?.quality = 5
                card?.update(LocalDateTime.now())
                Toast.makeText(this@MainActivity, String.format("%.1f", card?.easiness), Toast.LENGTH_SHORT).show()
            }
            doubtButton.setOnClickListener {
                card?.quality = 3
                card?.update(LocalDateTime.now())
                Toast.makeText(this@MainActivity, String.format("%.1f", card?.easiness), Toast.LENGTH_SHORT).show()
            }
            hardButton.setOnClickListener {
                card?.quality = 0
                card?.update(LocalDateTime.now())
                Toast.makeText(this@MainActivity, String.format("%.1f", card?.easiness), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
