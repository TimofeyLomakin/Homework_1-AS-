package com.example.lesson_1.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1.R
import com.example.lesson_1.databinding.ActivityMainBinding
import com.example.lesson_1.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fun translate(count: Long): String {
            if ((count / 1_000_000).toInt() != 0) {
                return ((count / 100_000).toDouble() / 10).toString() + "M"
            } else if ((count / 1_000).toInt() != 0) {
                return ((count / 100).toDouble() / 10).toString() + "K"
            }
            return count.toString()
        }

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likesCount.text = translate(post.likesCounter)
                shareCount.text = translate(post.shareCounter)
                viewsCount.text = translate(post.viewsCounter)
                likes.setImageResource(
                    if (post.likeByMe) {
                        R.drawable.ic_liked_24
                    } else {
                        R.drawable.ic_like_24
                    }
                )
                likes.setOnClickListener {
                    viewModel.like()
                }
                share.setOnClickListener {
                    viewModel.shareCount()
                }
            }

        }
        binding.likes.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.shareCount()
        }

    }
}