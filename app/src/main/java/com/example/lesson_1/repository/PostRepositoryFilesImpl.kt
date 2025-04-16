package com.example.lesson_1.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lesson_1.R
import com.example.lesson_1.dto.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class PostRepositoryFilesImpl(private val context: Context) : PostRepository {
    private var nextId = 1L
    private var posts = emptyList<Post>()
        set(value) {
            field = value
            data.value = posts
            sync()
        }
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(FILENAME)
        if (file.exists()) {
            context.openFileInput(FILENAME).bufferedReader().use {
                posts = gson.fromJson(it, type)
                nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                data.value = posts
            }
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

    private fun sync() {
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    companion object {
        private const val FILENAME = "posts.json"
        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
        private val gson = Gson()
    }
}