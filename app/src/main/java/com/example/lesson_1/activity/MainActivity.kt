package com.example.lesson_1.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_1.R
import com.example.lesson_1.adapter.OnInteractionListener
import com.example.lesson_1.adapter.PostsAdapter
import com.example.lesson_1.databinding.ActivityMainBinding
import com.example.lesson_1.dto.Post
import com.example.lesson_1.util.AndroidUtils
import com.example.lesson_1.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                binding.cancelGroup.visibility = View.VISIBLE
                binding.editText.text = post.content
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts)
            if (newPost) {
                binding.list.scrollToPosition(0)
            }
        }


        viewModel.edited.observe(this)
        {
            if (it.id != 0L) {
                binding.content.setText(it.content)
                binding.content.requestFocus()
            }
        }

        binding.add.setOnClickListener {
            binding.cancelGroup.visibility = View.GONE
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                Toast.makeText(
                    this@MainActivity,
                    R.string.error_empty_content,
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            viewModel.changeContentAndSave(text)

            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(it)
        }

        binding.cancel.setOnClickListener {
            viewModel.changeContentAndSave("cancel_edit")
            binding.content.setText("")
            binding.content.clearFocus()
            binding.cancelGroup.visibility = View.GONE
        }
    }
}


