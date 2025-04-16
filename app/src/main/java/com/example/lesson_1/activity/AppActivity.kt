package com.example.lesson_1.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.lesson_1.R
import com.example.lesson_1.activity.NewPostFragment.Companion.textArg
import com.example.lesson_1.databinding.ActivityAppBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, "Content can't be empty", LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
                return@let
            }
            findNavController(R.id.nav_controller).navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    textArg = text
                }
            )
        }
    }
}