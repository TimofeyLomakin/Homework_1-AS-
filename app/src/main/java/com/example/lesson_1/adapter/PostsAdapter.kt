package com.example.lesson_1.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView;
import com.example.lesson_1.R;
import com.example.lesson_1.activity.translate
import com.example.lesson_1.databinding.CardPostBinding;
import com.example.lesson_1.dto.Post;


typealias OnLikeListener = (Post) -> Unit
typealias OnShareListener = (Post) -> Unit
class PostsAdapter (private val onLikeListener: OnLikeListener, private val onShareListener : OnShareListener) : ListAdapter<Post, PostViewHolder> (PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val onLikeListener: OnLikeListener,
        private val onShareListener : OnShareListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.setImageResource(
                if (post.likeByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            likes.setOnClickListener {
                onLikeListener(post)
            }
            share.setOnClickListener {
                onShareListener(post)
            }
            likesCount.text = translate(post.likesCounter)
            shareCount.text = translate(post.shareCounter)
            viewsCount.text = translate(post.viewsCounter)
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}