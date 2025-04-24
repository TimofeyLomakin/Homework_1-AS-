package com.example.lesson_1.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesson_1.R
import com.example.lesson_1.activity.NewPostFragment.Companion.textArg
import com.example.lesson_1.databinding.FragmentPostBinding
import com.example.lesson_1.viewmodel.PostViewModel

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val postId = arguments?.getLong("postId") ?: 0L

        binding.post.menu.setOnClickListener { view ->
            PopupMenu(view.context, view).apply {
                inflate(R.menu.options_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            viewModel.data.value?.find { it.id == postId }?.let { post ->
                                viewModel.edit(post)
                                findNavController().navigate(
                                    R.id.action_postFragment_to_newPostFragment,
                                    Bundle().apply { textArg = post.content }
                                )
                            }
                            true
                        }

                        R.id.remove -> {
                            viewModel.removeById(postId)
                            findNavController().navigateUp()
                            true
                        }

                        else -> false
                    }
                }
            }.show()
        }

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            posts.find { it.id == postId }?.let { post ->
                with(binding.post) {
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    likes.text = translate(post.likesCounter)
                    share.text = translate(post.shareCounter)
                    viewsCount.text = translate(post.viewsCounter)

                    likes.setOnClickListener { viewModel.likeById(post.id) }
                    share.setOnClickListener { viewModel.shareById(post.id) }

                    likes.isChecked = post.likeByMe

                    youtubeGroup.visibility =
                        if (post.youtubeUrl.isNotEmpty()) View.VISIBLE else View.GONE
                }
            }
        }

        return binding.root
    }
}