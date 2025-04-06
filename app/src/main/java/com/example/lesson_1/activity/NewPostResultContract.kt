package com.example.lesson_1.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract


object NewPostResultContract : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, NewPostActivity::class.java).apply {
            putExtra("content", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(Intent.EXTRA_TEXT)
    }
}