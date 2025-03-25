package com.example.lesson_1.repository

import androidx.lifecycle.LiveData
import com.example.lesson_1.dto.Post

interface PostRepository {
    fun get() : LiveData<Post>
    fun like()
    fun shareCount()

}