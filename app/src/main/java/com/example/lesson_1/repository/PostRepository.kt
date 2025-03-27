package com.example.lesson_1.repository

import androidx.lifecycle.LiveData
import com.example.lesson_1.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}