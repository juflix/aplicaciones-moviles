package com.example.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.cards.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var card = Card("Tree", "Árbol")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.card = card
        binding.apply {
            answerButton.setOnClickListener {
                card?.answered = true
                invalidateAll()
            }
        }
        Timber.i("onCreate called")
        Timber.i("answered = ${savedInstanceState?.getBoolean(Companion.ANSWERED_KEY)}")

        // card.answered = savedInstanceState?.getBoolean(ANSWERED_KEY) ?: false
        // binding.invalidateAll()
    }

    override fun onStart(){
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
        binding.invalidateAll()
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState called")
        outState.putBoolean(Companion.ANSWERED_KEY, card.answered)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.i("onRestoreInstanceState called")
        card.answered = savedInstanceState?.getBoolean(ANSWERED_KEY) ?: false

    }

    companion object {
        private const val ANSWERED_KEY = "com.example.cards:answered"
    }

}

