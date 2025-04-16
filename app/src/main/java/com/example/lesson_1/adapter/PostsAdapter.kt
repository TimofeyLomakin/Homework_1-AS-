package com.example.lesson_1.adapter;

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater;
import android.view.View
import android.view.ViewGroup;
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView;
import com.example.lesson_1.R;
import com.example.lesson_1.activity.translate
import com.example.lesson_1.databinding.CardPostBinding;
import com.example.lesson_1.dto.Post;


interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onPostClick(post: Post) {}
}

class PostsAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) = with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        if (post.youtubeUrl != "") {
            youtubeGroup.visibility = View.VISIBLE
        } else {
            youtubeGroup.visibility = View.GONE
        }
        likes.apply {
            isChecked = post.likeByMe
            text = translate(post.likesCounter)
        }
        share.text = translate(post.shareCounter)

        menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            onInteractionListener.onRemove(post)
                            true
                        }

                        R.id.edit -> {
                            onInteractionListener.onEdit(post)
                            true
                        }

                        else -> false
                    }
                }
            }.show()
        }
        likes.setOnClickListener {
            onInteractionListener.onLike(post)
        }
        share.setOnClickListener {
            onInteractionListener.onShare(post)
        }
        viewsCount.text = translate(post.viewsCounter)

        youtubeLogo.setOnClickListener {
            startYouTube(post.youtubeUrl)
        }
        playYoutube.setOnClickListener {
            startYouTube(post.youtubeUrl)
        }
        itemView.setOnClickListener { view ->
            if (!isClickOnInteractiveElement(view)) {
                onInteractionListener.onPostClick(post)
            }
        }
    }

    private fun startYouTube(youtubeUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        itemView.context.startActivity(intent)
    }
    private fun isClickOnInteractiveElement(view: View): Boolean {
        return when (view.id) {
            R.id.menu, R.id.likes, R.id.share,
            R.id.youtubeLogo, R.id.playYoutube -> true
            else -> false
        }
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}