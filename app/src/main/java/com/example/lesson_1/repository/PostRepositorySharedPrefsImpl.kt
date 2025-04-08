package com.example.lesson_1.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesson_1.R
import com.example.lesson_1.dto.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class PostRepositorySharedPrefsImpl(context : Context) : PostRepository {
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var nextId = 1L
    private var posts = emptyList<Post>()

        set(value) {
            field = value
            data.value = posts
            sync()
        }
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(KEY_POSTS, null)?.let{
            posts = gson.fromJson(it, type)
            nextId = (posts.maxOfOrNull{it.id}?: 0) + 1
            data.value = posts
        }
    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id != id) post else {
                val newLikes = if (post.likeByMe) post.likesCounter - 1 else post.likesCounter + 1

                post.copy(likeByMe = !post.likeByMe, likesCounter = newLikes)
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCounter = it.shareCounter + 1)
        }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me"
                )
            ) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    private fun sync(){
        prefs.edit {
            putString(KEY_POSTS, gson.toJson(posts))
        }
    }

    companion object {
        private const val KEY_POSTS = "posts"
        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
        private val gson = Gson()
    }
}