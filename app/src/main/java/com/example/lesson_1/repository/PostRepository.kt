package com.example.lesson_1.repository

import androidx.lifecycle.LiveData
import com.example.lesson_1.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun likeById(id: Long, likeByMe: Boolean): Post
    fun shareById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
}