package com.example.lesson_1.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lesson_1.R
import com.example.lesson_1.activity.NewPostFragment.Companion.textArg
import com.example.lesson_1.adapter.OnInteractionListener
import com.example.lesson_1.adapter.PostsAdapter
import com.example.lesson_1.databinding.FragmentFeedBinding
import com.example.lesson_1.dto.Post
import com.example.lesson_1.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content })
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

            override fun onPostClick(post: Post) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_postFragment,
                    Bundle().apply { putLong("postId", post.id) }
                )
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts)
            if (newPost) {
                binding.list.scrollToPosition(0)
            }

        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}


