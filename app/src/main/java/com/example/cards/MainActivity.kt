package com.example.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cards.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var listener = View.OnClickListener { v ->
        val quality = when (v?.id) {
            R.id.hard_button -> 0
            R.id.doubt_button -> 3
            R.id.easy_button -> 5
            else -> throw Exception("Quality button unknown")
        }
        viewModel.update(quality)
        if (viewModel.card == null){
            Toast.makeText(this@MainActivity,
                "No more cards to review",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.invalidateAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.answerButton.setOnClickListener {
            viewModel.card?.answered = true
            binding.invalidateAll()
        }

        binding.hardButton.setOnClickListener(listener)
        binding.doubtButton.setOnClickListener(listener)
        binding.easyButton.setOnClickListener(listener)

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

}

